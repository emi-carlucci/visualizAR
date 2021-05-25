package edu.ort.visualizar.fragments

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import edu.ort.visualizar.R

import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.models.KpiModel
import edu.ort.visualizar.utils.OCBUtils

class AltaIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var inputID : TextView
    lateinit var txtCheckResult : TextView
    lateinit var btnCheckID : Button
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var spFrecuencia : Spinner
    lateinit var txtFrecuencia : String
    lateinit var spCategoria : Spinner
    lateinit var txtCategoria : String
    lateinit var inputCheck1 : CheckBox
    var calculationMethod : String? = null
    lateinit var inputCheck2 : CheckBox
    var source : String? = null
    lateinit var inputCheck3 : CheckBox
    var businessTarget : String? = null
    lateinit var inputCheck4 : CheckBox
    var dateNextCalculation : String? = null
    lateinit var inputCheck5 : CheckBox
    var address : String? = null
    lateinit var inputCheck6 : CheckBox
    var area : String? = null
    lateinit var btnConfirm : Button
    lateinit var btnLimpiar : Button

    var listaFrecuencia = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var listaCategoria = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")

    var validarAction : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_alta_indicador, container, false)
        inputID  = v.findViewById(R.id.inputAltaID)
        txtCheckResult = v.findViewById(R.id.textCheckResult)
        btnCheckID = v.findViewById(R.id.btnAltaCheckID)
        inputTitulo = v.findViewById(R.id.inputAltaTitulo)
        inputDescripcion = v.findViewById(R.id.inputAltaDescripcion)
        inputFormula = v.findViewById(R.id.inputAltaFormula)
        spFrecuencia = v.findViewById(R.id.spAltaFrecuencia)
        txtFrecuencia = ""
        spCategoria = v.findViewById(R.id.spAltaCategoria)
        txtCategoria = ""
        inputCheck1 = v.findViewById(R.id.checkBox1)
        inputCheck2 = v.findViewById(R.id.checkBox2)
        inputCheck3 = v.findViewById(R.id.checkBox3)
        inputCheck4 = v.findViewById(R.id.checkBox4)
        inputCheck5 = v.findViewById(R.id.checkBox5)
        inputCheck6 = v.findViewById(R.id.checkBox6)
        btnConfirm = v.findViewById(R.id.btnAltaConfirmar)
        btnLimpiar = v.findViewById(R.id.btnAltaLimpiar)

        return v
    }

    override fun onStart() {
        super.onStart()

        populateSpinner(spFrecuencia,listaFrecuencia,requireContext())
        populateSpinner(spCategoria,listaCategoria,requireContext())

        spFrecuencia.setSelection(0, false) // evita la primer falsa entrada si existe validación
        spCategoria.setSelection(0, false) // evita la primer falsa entrada si existe validación

        spFrecuencia.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaFrecuencia[position], Snackbar.LENGTH_SHORT).show()
                txtFrecuencia = listaFrecuencia[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })

        spCategoria.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaCategoria[position], Snackbar.LENGTH_SHORT).show()
                txtCategoria = listaCategoria[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })

        btnCheckID.setOnClickListener {
            validarID(inputID)
        }

        btnConfirm.setOnClickListener{
            if(inputID.text.toString().equals("")){
                Snackbar.make(v, "Ingrese el ID.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }else{
                validarAction = validarID(inputID)
            }
            if(inputTitulo.text.toString().equals("")) {
                Snackbar.make(v, "Ingrese el título.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(inputDescripcion.text.toString().equals("")){
                Snackbar.make(v, "Ingrese la descripción.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(inputFormula.text.toString().equals("")){
                Snackbar.make(v, "Ingrese la fórmula.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(validarAction){
                var anio = Calendar.getInstance().get(Calendar.YEAR)
                var mes = Calendar.getInstance().get(Calendar.MONTH)
                var dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                var hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                println(hora.toString())

                var calculationPeriodFrom : String = anio.toString()+"-"+mes.toString()+"-"+dia.toString()
                var calculationPeriodTo : String = anio.toString()+"-"+mes.toString()+"-"+dia.toString()

                        // Aca se persiste en la BD
                println("")
                println("GRABANDO....")
                println(inputID.text.toString())
                println(inputTitulo.text)
                println(inputDescripcion.text)
                println(inputFormula.text)
                println(txtFrecuencia)
                println(txtCategoria)
                println(calculationPeriodFrom)
                println(calculationPeriodTo)

                if(inputCheck1.isChecked) {
                    println(inputCheck1.text)
                    calculationMethod =""
                }
                if(inputCheck2.isChecked) {
                    println(inputCheck2.text)
                    source = ""
                }
                if(inputCheck3.isChecked) {
                    println(inputCheck3.text)
                    businessTarget = ""
                }
                if(inputCheck4.isChecked) {
                    println(inputCheck4.text)
                    dateNextCalculation = anio.toString()+"-"+mes.toString()+"-"+dia.toString()
                }
                if(inputCheck5.isChecked) {
                    println(inputCheck5.text)
                    address = ""
                }
                if(inputCheck6.isChecked) {
                    println(inputCheck6.text)
                    area = ""
                }

                var ocb = OCBUtils()

                ocb.createKpi(inputID.text.toString(),txtCategoria,txtFrecuencia,inputDescripcion.text.toString(),null,null,address!!,calculationPeriodFrom,calculationPeriodTo,dateNextCalculation,
                        calculationMethod!!,null,"Ciudades Futuras","",inputTitulo.text.toString(),source!!,null,businessTarget!!,inputFormula.text.toString()!!,null,null, area!!)

                // vuelvo a HOME
                 val action = AltaIndicadorFragmentDirections.actionAltaIndicadorFragmentToHomeFragment()
                 v.findNavController().navigate(action)

            }
            validarAction=true
        }
        btnLimpiar.setOnClickListener {
            inputID.setText("")
            txtCheckResult.setText("")
            inputTitulo.setText("")
            inputDescripcion.setText("")
            inputFormula.setText("")
            inputCheck1.setChecked(false)
            inputCheck2.setChecked(false)
            inputCheck3.setChecked(false)
            inputCheck4.setChecked(false)
            inputCheck5.setChecked(false)
            inputCheck6.setChecked(false)
            validarAction=true
        }
    }

    fun populateSpinner (spinner: Spinner, list : List<String>, context : Context)
    {
        //   val aa = ArrayAdapter( context!!, android.R.layout.simple_spinner_item, list)
        val aa = ArrayAdapter(context,android.R.layout.simple_spinner_item, list)

        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.setAdapter(aa)
    }

    fun validarID(id : TextView) : Boolean {
        var result: Boolean = false
        if(id.text.toString().equals("")){
            Snackbar.make(v, "Ingrese el ID.", Snackbar.LENGTH_SHORT).show()
            result = false
        }else{
            var ocb = OCBUtils()
            var indicador : KpiModel? = ocb.getKpi(inputID.text.toString())
            if(indicador==null){
                txtCheckResult.setText("DISPONIBLE")
                Snackbar.make(v, "ID disponible", Snackbar.LENGTH_SHORT).show()
                result = true
            }else{
                txtCheckResult.setText("NO DISPONIBLE")
                Snackbar.make(v, "ID existente", Snackbar.LENGTH_SHORT).show()
                result = false
            }
        }
        return result
    }
}