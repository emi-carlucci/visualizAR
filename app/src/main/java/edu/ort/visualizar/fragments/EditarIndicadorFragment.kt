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

class EditarIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var inputID : TextView
    lateinit var txtCheckResult : TextView
    lateinit var btnCargarKpi : Button
    lateinit var inputOrganization : TextView
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var spFrecuencia : Spinner
    lateinit var txtFrecuencia : String
    lateinit var spCategoria : Spinner
    lateinit var txtCategoria : String
    lateinit var spCalculationMethod : Spinner
    lateinit var txtCalculationMethod : String
    lateinit var inputCheck1 : CheckBox

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
    lateinit var btnRestore : Button

    var listaFrecuencia = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var listaCategoria = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
    var listaCalculationMethod = listOf("manual", "automatic", "semiautomatic")

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
        inputID  = v.findViewById(R.id.inputAltaID)
        txtCheckResult = v.findViewById(R.id.textCheckResult)
        btnCargarKpi = v.findViewById(R.id.btnAltaCargarKpi)
        inputOrganization = v.findViewById(R.id.inputAltaOrganization)
        inputTitulo = v.findViewById(R.id.inputAltaTitulo)
        inputDescripcion = v.findViewById(R.id.inputAltaDescripcion)
        inputFormula = v.findViewById(R.id.inputAltaFormula)
        spFrecuencia = v.findViewById(R.id.spAltaFrecuencia)
        txtFrecuencia = ""
        spCategoria = v.findViewById(R.id.spAltaCategoria)
        txtCategoria = ""
        spCalculationMethod = v.findViewById(R.id.spAltaCalculationMethod)
        txtCalculationMethod = ""
        inputCheck1 = v.findViewById(R.id.checkBox1)
        inputCheck2 = v.findViewById(R.id.checkBox2)
        inputCheck3 = v.findViewById(R.id.checkBox3)
        inputCheck4 = v.findViewById(R.id.checkBox4)
        inputCheck5 = v.findViewById(R.id.checkBox5)
        inputCheck6 = v.findViewById(R.id.checkBox6)
        btnConfirm = v.findViewById(R.id.btnAltaConfirmar)
        btnRestore = v.findViewById(R.id.btnAltaRestaurar)


        // Cargo la data a los INPUTS
        indicador = EditarIndicadorFragmentArgs.fromBundle(requireArguments()).indicador
        cargarForm(indicador)

        return v
    }

    override fun onStart() {
        super.onStart()

        populateSpinner(spFrecuencia,listaFrecuencia,requireContext())
        populateSpinner(spCategoria,listaCategoria,requireContext())
        populateSpinner(spCalculationMethod,listaCalculationMethod,requireContext())

        spFrecuencia.setSelection(0, false) // evita la primer falsa entrada si existe validación
        spCategoria.setSelection(0, false) // evita la primer falsa entrada si existe validación
        spCalculationMethod.setSelection(0, false) // evita la primer falsa entrada si existe validación

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

        btnCargarKpi.setOnClickListener {
            traerKpi(inputID)
        }

        btnConfirm.setOnClickListener{
            /*
            if(inputID.text.toString().equals("")){
                Snackbar.make(v, "Ingrese el ID.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }else{
                validarAction = validarID(inputID)
            }
            */
            if(inputOrganization.text.toString().equals("")) {
                Snackbar.make(v, "Ingrese la organización.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
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
                var updateAt : String = anio.toString()+"-"+mes.toString()+"-"+dia.toString()

                // Aca se persiste en la BD
                println("")
                println("GRABANDO....")
                println(inputID.text.toString())
                println(inputOrganization.text.toString())
                println(inputTitulo.text)
                println(inputDescripcion.text)
                println(inputFormula.text)
                println(txtFrecuencia)
                println(txtCategoria)
                println(calculationPeriodFrom)
                println(calculationPeriodTo)

                /*
                if(inputCheck1.isChecked) {
                    println(inputCheck1.text)
                    txtCalculationMethod =""
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
                */

                var ocb = OCBUtils()

                ocb.updateKpi(inputID.text.toString(),txtCategoria,txtFrecuencia,inputDescripcion.text.toString(),null,null,address!!,calculationPeriodFrom,calculationPeriodTo,dateNextCalculation,
                        txtCalculationMethod!!,null,inputOrganization.text.toString()!!,inputTitulo.text.toString(),source!!,null,businessTarget!!,inputFormula.text.toString()!!,null,updateAt,area!!)

                // vuelvo a HOME
                val action = AltaIndicadorFragmentDirections.actionAltaIndicadorFragmentToHomeFragment()
                v.findNavController().navigate(action)

            }
            validarAction=true
        }
        btnRestore.setOnClickListener {
            traerKpi(inputID)
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

    fun traerKpi(id : TextView) : Boolean {
        var result: Boolean = false
        if(id.text.toString().equals("")){
            Snackbar.make(v, "Ingrese el ID.", Snackbar.LENGTH_SHORT).show()
            result = false
        }else{
            var ocb = OCBUtils()
            var indicador : KpiModel? = ocb.getKpi(inputID.text.toString())
            if(indicador==null){
                txtCheckResult.setText("ID no encontrado")
                Snackbar.make(v, "ID no encontrado", Snackbar.LENGTH_SHORT).show()
                result = false
            }else{
                txtCheckResult.setText("Cargando datos....")
                cargarForm(indicador)
                result = true
            }
        }
        return result
    }

    fun cargarForm(indicador : KpiModel? ) {
        inputID.setText(indicador!!.id?.toString())
        inputOrganization.setText(indicador!!.organization?.value?.name?.toString())
        inputTitulo.setText(indicador!!.name?.value?.toString())
        inputDescripcion.setText(indicador!!.description?.value?.toString())
        inputFormula.setText(indicador!!.calculationFormula?.value?.toString())

/*
        if(indicador.calculationMethod!!.value.toString() != null){
            inputCheck1.setChecked(true)
            inputCheck1.setText(indicador.calculationMethod!!.value.toString())
        }else{
            inputCheck1.setChecked(false)
        }
        if(indicador.source!!.value.toString() != null) {
            inputCheck2.setChecked(true)
            inputCheck2.setText(indicador.source!!.value.toString())
        }else{
            inputCheck2.setChecked(false)
        }
        if(indicador.businessTarget!!.value.toString() != null) {
            inputCheck3.setChecked(true)
            inputCheck3.setText(indicador.businessTarget!!.value.toString())
        }else{
            inputCheck3.setChecked(false)
        }
        if(indicador.dateNextCalculation!!.value.toString() != null) {
            inputCheck4.setChecked(true)
            inputCheck4.setText(indicador.dateNextCalculation!!.value.toString())
        }else{
            inputCheck4.setChecked(false)
        }
        if(indicador.address!!.value.toString() != null) {
            inputCheck5.setChecked(true)
            inputCheck5.setText(indicador.address!!.value.toString())
        }else{
            inputCheck5.setChecked(false)
        }
        if(indicador.area!!.value.toString() != null) {
            inputCheck6.setChecked(true)
            inputCheck6.setText(indicador.area!!.value.toString())
        }else{
            inputCheck6.setChecked(false)
        }
//        validarAction=true
        txtCheckResult.setText("")

 */
    }
}