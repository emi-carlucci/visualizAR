package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import edu.ort.visualizar.R

import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.models.*
import edu.ort.visualizar.utils.OCBUtils

class EditarIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var btnConfirm : Button
    lateinit var btnRestablecer : Button
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var inputFrec : TextView
    lateinit var spinner : Spinner
    var listaFrec = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var listaCat = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
    var validarAction : Boolean = true

    private var indicador: KpiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_editar_indicador, container, false)
        btnConfirm = v.findViewById(R.id.btnConfirmar)
        btnRestablecer = v.findViewById(R.id.btnRestablecer)
        inputTitulo = v.findViewById(R.id.inputIndicadorTitulo)
        inputDescripcion = v.findViewById(R.id.inputIndicadorDescripcion)
        inputFormula = v.findViewById(R.id.inputFormula)
        inputFrec = v.findViewById(R.id.inputFrecuencia)
        return v
    }

    override fun onStart() {
        super.onStart()

        super.onStart()
        var ocb = OCBUtils()
        var indicador : KpiModel? = ocb.getKpi("kpi-2016-Ciudad-containers-faults")
        //TODO chequear que no esté nulo
        if (indicador != null) {
            var nombreKpi : Name? = indicador.name
            if (nombreKpi != null) {
                inputTitulo.text = nombreKpi.value.toString()
            }
            var descripcion : Description? = indicador.description
            if (descripcion != null) {
                inputTitulo.text = descripcion.value.toString()
            }
            var formula : CalculationFormula? = indicador.calculationFormula
            if (formula != null) {
                inputTitulo.text = formula.value.toString()
            }
            var calculationFrequency :CalculationFrequency? = indicador.calculationFrequency
            if (calculationFrequency != null) {
                inputTitulo.text = calculationFrequency.value.toString()
            }
        }


        btnConfirm.setOnClickListener{
            if(inputTitulo.text.toString().equals("")) {
                Snackbar.make(v, "Ingrese el título.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(inputDescripcion.text.toString().equals("")){
                Snackbar.make(v, "Ingrese la descripción.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            //formula
            //Frequency

            if(validarAction){
                // Aca se persiste en la BD
                println("GRABANDO....")
                println(inputTitulo.text)
                println(inputDescripcion.text)

                var ocb = OCBUtils()
                /*ocb.updateKpi(indicador.id.toString(), txtCat, txtFrec, inputDescripcion.text.toString(),null, address,null,null,null,
                        dateNextCalculation, calculationMethod,null,"Ciudades Futuras","", inputTitulo.text.toString(), source ,null, businessTarget,
                        inputFormula.text.toString(),null,null)*/

                Thread.sleep(500)
                println("vuelvo a HOME")

            }
            validarAction=true
        }
        btnRestablecer.setOnClickListener { // Aca deberia volver a la version anterior (la original que se estaba editando)
            if (indicador != null) {
                var nombreKpi : Name? = indicador.name
                if (nombreKpi != null) {
                    inputTitulo.text = nombreKpi.value.toString()
                }
                var descripcion : Description? = indicador.description
                if (descripcion != null) {
                    inputTitulo.text = descripcion.value.toString()
                }
                var formula : CalculationFormula? = indicador.calculationFormula
                if (formula != null) {
                    inputTitulo.text = formula.value.toString()
                }
                var calculationFrequency :CalculationFrequency? = indicador.calculationFrequency
                if (calculationFrequency != null) {
                    inputTitulo.text = calculationFrequency.value.toString()
                }
            }
        }
    }

}
