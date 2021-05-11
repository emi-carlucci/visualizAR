package edu.ort.visualizar.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import edu.ort.visualizar.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "nombre de la variable"
private const val ARG_PARAM3 = "73%"
private const val ARG_PARAM4 = "2021/05/03 10:31:24"


/**
 * A simple [Fragment] subclass.
 * Use the [fragment_acciones.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccionesIndicadorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var v : View
    lateinit var btnEdit : Button
    lateinit var btnDelete : Button
    lateinit var btnUpdate : Button
    lateinit var tvIndicatorName : TextView
    lateinit var tvIndicatorValue: TextView
    lateinit var tvLastUpdateDate : TextView

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_acciones_indicador, container, false)
        btnEdit = v.findViewById(R.id.btnAccionesEditIndicator)
        btnDelete = v.findViewById(R.id.btnAccionesDeleteIndicator)
        btnUpdate = v.findViewById(R.id.btnAccionesUpdateValue)
        tvIndicatorName = v.findViewById(R.id.tvAccionesIndicatorName)
        tvIndicatorValue = v.findViewById(R.id.tvAccionesIndicatorValue)
        tvLastUpdateDate = v.findViewById(R.id.tvAccionesLastUpdateDate)
        return v
    }

    override fun onStart() {
        super.onStart()
        var indicadorId = AccionesIndicadorFragmentArgs.fromBundle(requireArguments()).indicadorId
        tvIndicatorName.text = indicadorId.toString()
        tvIndicatorValue.text = ARG_PARAM3
        tvLastUpdateDate.text = ARG_PARAM4
        btnEdit.setOnClickListener{
            val action = AccionesIndicadorFragmentDirections.actionAccionesIndicadorFragmentToEditarIndicadorFragment(indicadorId)
            v.findNavController().navigate(action)
        }
        btnDelete.setOnClickListener{
            var builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.confirm_delete))
            builder.setPositiveButton(getString(R.string.delete), DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            builder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            var alert = builder.create()
            alert.show()
        }
        btnUpdate.setOnClickListener{
            val action =
                AccionesIndicadorFragmentDirections.actionAccionesIndicadorFragmentToActualizarValorFragment(indicadorId)
            v.findNavController().navigate(action)
        }


    }



    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_acciones.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_acciones().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}