package edu.ort.visualizar.fragments

import android.annotation.SuppressLint
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
import edu.ort.visualizar.R
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.models.KpiModel

class ActualizarValorFragment : Fragment() {

    lateinit var v : View
    lateinit var btnActualizarValor : Button
    lateinit var textViewUpdateIndicadorValorActualValue : TextView
    lateinit var textViewUpdateIndicadorName: TextView
    lateinit var editTextUpdateIndicadorNewValueInput: EditText
    lateinit var textViewFormualaIndicadorName: TextView
    private var indicator: KpiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val indicatorId = ActualizarValorFragmentArgs.fromBundle(requireArguments()).indicadorId
        if (indicatorId != null) {
            indicator = ocbUtils.getKpi(indicatorId)
        }
        if (indicator == null){
            Toast.makeText(activity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
            redirectToHome()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_actualizar_valor_indicador, container, false)
        btnActualizarValor = v.findViewById(R.id.buttonUpdateIndicadorValue)
        textViewUpdateIndicadorValorActualValue = v.findViewById(R.id.textViewUpdateIndicadorValorActualValue)
        textViewUpdateIndicadorName = v.findViewById(R.id.textViewUpdateIndicadorName)
        editTextUpdateIndicadorNewValueInput = v.findViewById(R.id.editTextUpdateIndicadorNewValueInput)
        textViewFormualaIndicadorName = v.findViewById(R.id.textViewUpdateIndicadorFormula)
        textViewUpdateIndicadorValorActualValue.text = indicator?.kpiValue!!.value.toString()
        textViewUpdateIndicadorName.text = indicator?.name!!.value.toString()
        if (indicator!!.calculationFormula != null){
            textViewFormualaIndicadorName.text = indicator!!.calculationFormula!!.value.toString()
        } else {
            textViewFormualaIndicadorName.text = "NO DEFINIDA"
        }
        return v
    }

    override fun onStart() {
        super.onStart()
        btnActualizarValor.setOnClickListener{
            if (!isValidInput(editTextUpdateIndicadorNewValueInput.text.toString())) {
                Toast.makeText(activity, "Por favor ingresá un valor", Toast.LENGTH_SHORT).show()
            } else {
                val result = ocbUtils.updateKpiValue(indicator?.id.toString(), editTextUpdateIndicadorNewValueInput.text.toString())
                if (result) {
                    redirectToHome()
                } else {
                    Toast.makeText(activity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun redirectToHome(){
        val action = indicator?.let { ActualizarValorFragmentDirections.actionActualizarValorFragmentToAccionesIndicadorFragment2(it) }
        if (action != null) {
            v.findNavController().navigate(action)
        }
        v.findNavController().popBackStack(R.id.actualizarValorFragment, true)
    }

    private fun isValidInput(input: String): Boolean {
        var isValid = true
        if (input.isEmpty()) {
            isValid = false
        }
        return isValid
    }

}