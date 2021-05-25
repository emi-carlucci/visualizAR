package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.ort.visualizar.R
import edu.ort.visualizar.models.KpiModel
import kotlin.reflect.full.memberProperties

class VerMasIndicadorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var v : View

    lateinit var indicador: KpiModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        indicador = AccionesIndicadorFragmentArgs.fromBundle(requireArguments()).indicador!!
        v = inflater.inflate(R.layout.fragment_ver_mas_indicador, container, false)
        val linearLayout: LinearLayout = v.findViewById(R.id.verMasLayout)
        for (prop in KpiModel::class.memberProperties) {
            val textView = TextView(this.context)
            textView.layoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            textView.gravity = Gravity.CENTER
            textView.setText("${prop.name} = ${prop.get(indicador).toString()}")


            // Add TextView to LinearLayout

            // Add TextView to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(textView)
            }
            println("${prop.name} = ${prop.get(indicador)}")
        }


        return v
    }


}