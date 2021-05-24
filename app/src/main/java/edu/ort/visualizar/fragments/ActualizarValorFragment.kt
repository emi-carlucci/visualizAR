package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ort.visualizar.R
import edu.ort.visualizar.models.KpiModel
import edu.ort.visualizar.utils.OCBUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActualizarValorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActualizarValorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var indicador: KpiModel
    lateinit var v : View
    lateinit var btnActualizarValor : Button
    lateinit var textViewUpdateIndicadorValorActualValue : TextView
    lateinit var textViewUpdateIndicadorName: TextView
    lateinit var editTextUpdateIndicadorNewValueInput: EditText
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indicador = ActualizarValorFragmentArgs.fromBundle(requireArguments()).indicador!!


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_actualizar_valor_indicador, container, false)
        btnActualizarValor = v.findViewById(R.id.buttonUpdateIndicadorValue)
        textViewUpdateIndicadorValorActualValue = v.findViewById(R.id.textViewUpdateIndicadorValorActualValue)
        textViewUpdateIndicadorName = v.findViewById(R.id.textViewUpdateIndicadorName)
        if (indicador !== null){
            textViewUpdateIndicadorValorActualValue.setText(indicador!!.kpiValue!!.value.toString())
            textViewUpdateIndicadorName.setText(indicador!!.name!!.value.toString())
        }
        editTextUpdateIndicadorNewValueInput = v.findViewById(R.id.editTextUpdateIndicadorNewValueInput)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnActualizarValor.setOnClickListener{
            if (!isValidInput(editTextUpdateIndicadorNewValueInput.text.toString())) {
                Toast.makeText(getActivity(), "Por favor ingrese un valor", Toast.LENGTH_SHORT).show()
            } else {
                saveNewValue(editTextUpdateIndicadorNewValueInput.text.toString())
                redirectToHome()
            }
        }
    }

    private fun saveNewValue(newValue: String) {
        var ocbUtils = OCBUtils()
        ocbUtils.updateKpiValue(indicador?.id.toString(), newValue)
    }

    private fun redirectToHome(){
        val action = ActualizarValorFragmentDirections.actionActualizarValorFragmentToAccionesIndicadorFragment2(indicador)
        v.findNavController().navigate(action)
    }

    private fun isValidInput(input: String): Boolean {
        var isValid = true
        if (input.isEmpty()) {
            isValid = false
        }
        return isValid
    }

}