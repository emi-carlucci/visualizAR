package edu.ort.visualizar.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.R
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.utils.DateUtils


class AccionesIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var btnEdit : Button
    lateinit var btnDelete : Button
    lateinit var btnUpdate : Button
    lateinit var tvIndicatorName : TextView
    lateinit var tvIndicatorValue: TextView
    lateinit var tvLastUpdateDate : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_acciones_indicador, container, false)
        btnEdit = v.findViewById(R.id.btnAccionesEditIndicator)
        btnDelete = v.findViewById(R.id.btnAccionesDeleteIndicator)
        btnUpdate = v.findViewById(R.id.btnAccionesUpdateValue)
        tvIndicatorName = v.findViewById(R.id.tvAccionesIndicatorName)
        tvIndicatorValue = v.findViewById(R.id.tvAccionesIndicatorValue)
        tvLastUpdateDate = v.findViewById(R.id.tvAccionesLastUpdateDate)
        return v
    }

    override fun onResume() {
        super.onResume()
        var indicator = AccionesIndicadorFragmentArgs.fromBundle(requireArguments()).indicador
        val updatedIndicator = ocbUtils.getKpi(indicator.id.toString())
        if (updatedIndicator != null){
            indicator = updatedIndicator
        }
        val parsedDate = DateUtils().parseDate(indicator.dateModified?.value.toString())
        tvIndicatorName.text = indicator.name!!.value.toString()
        tvIndicatorValue.text = indicator.kpiValue!!.value.toString()
        tvLastUpdateDate.text = parsedDate
        btnEdit.setOnClickListener{
            val action = AccionesIndicadorFragmentDirections.actionAccionesIndicadorFragmentToEditarIndicadorFragment(indicator)
            v.findNavController().navigate(action)
        }
        btnDelete.setOnClickListener{
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.confirm_delete))
            builder.setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                val deleteOk : Boolean = ocbUtils.deleteKpi(indicator.id.toString())
                dialog.cancel()
                if (deleteOk){
                    val action = AccionesIndicadorFragmentDirections.actionAccionesIndicadorFragmentToHomeFragment()
                    v.findNavController().navigate(action)
                } else {
                    val snackbar = Snackbar.make(v, getString(R.string.error_delete), Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.show()
        }
        btnUpdate.setOnClickListener{
            val action = AccionesIndicadorFragmentDirections.actionAccionesIndicadorFragmentToActualizarValorFragment(indicator)
            v.findNavController().navigate(action)
        }
    }
}