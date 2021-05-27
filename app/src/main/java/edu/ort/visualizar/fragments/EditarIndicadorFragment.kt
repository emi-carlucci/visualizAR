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
import edu.ort.visualizar.models.Address
import edu.ort.visualizar.models.BusinessTarget
import edu.ort.visualizar.models.DateNextCalculation
import edu.ort.visualizar.models.KpiModel
import edu.ort.visualizar.utils.OCBUtils
import org.w3c.dom.Text

class EditarIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var txtID : TextView
    lateinit var inputOrganization : TextView
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var spFrecuencia : Spinner
    lateinit var txtFrecuencia : String
    lateinit var spCategoria : Spinner
    lateinit var txtCategoria : String

    lateinit var inputCheck1 : CheckBox
    lateinit var spCalculationMethod : Spinner
    lateinit var txtCalculationMethod : String

    lateinit var inputCheck2 : CheckBox
    lateinit var inputSource : TextView
    lateinit var txtSource : String

    lateinit var inputCheck3 : CheckBox
    lateinit var inputBusinessTarget: TextView
    lateinit var txtBusinessTarget : String

    lateinit var inputCheck4 : CheckBox
    lateinit var inputDateNextCalculation : TextView
    lateinit var txtdateNextCalculation : String

    lateinit var inputCheck5 : CheckBox
    lateinit var inputArea : TextView
    lateinit var txtArea : String

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
        txtID  = v.findViewById(R.id.txtAltaID)
        inputOrganization = v.findViewById(R.id.inputAltaOrganization)
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
        btnConfirm = v.findViewById(R.id.btnAltaConfirmar)
        btnRestore = v.findViewById(R.id.btnAltaRestaurar)

        // Cargo la data a los INPUTS
        indicador = EditarIndicadorFragmentArgs.fromBundle(requireArguments()).indicador
        cargarForm(indicador)

        if(inputCheck1.isChecked) {
            spCalculationMethod = v.findViewById(R.id.spEditCalculationMethod)
        }
        if(inputCheck2.isChecked) {
            inputSource = v.findViewById(R.id.inputEditSource)
            inputSource.setText(indicador!!.source?.value?.toString())
        }
        if(inputCheck3.isChecked) {
            inputBusinessTarget = v.findViewById(R.id.inputEditTarget)
            inputBusinessTarget.setText(indicador!!.businessTarget?.value?.toString())
        }
        if(inputCheck4.isChecked) {
            inputDateNextCalculation = v.findViewById(R.id.inputDateNextCalculation)
            inputDateNextCalculation.setText(indicador!!.dateNextCalculation?.value?.toString())
        }
        if(inputCheck5.isChecked) {
            inputArea = v.findViewById(R.id.inputEditArea)
            inputArea.setText(indicador!!.area?.value?.toString())
        }

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
                Snackbar.make(v, "Seleccione la frecuencia", Snackbar.LENGTH_SHORT).show()
            }
        })

        spCategoria.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaCategoria[position], Snackbar.LENGTH_SHORT).show()
                txtCategoria = listaCategoria[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccione la categoría", Snackbar.LENGTH_SHORT).show()
            }
        })
        spCalculationMethod.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaCalculationMethod[position], Snackbar.LENGTH_SHORT).show()
                txtCalculationMethod = listaCalculationMethod[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccione el método", Snackbar.LENGTH_SHORT).show()
            }
        })

        btnRestore.setOnClickListener {
            cargarForm(indicador)
        }

        btnConfirm.setOnClickListener{

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
                println("Editando....")
                println(txtID.text.toString())
                println(inputOrganization.text.toString())
                println(inputTitulo.text)
                println(inputDescripcion.text)
                println(inputFormula.text)
                println(txtFrecuencia)
                println(txtCategoria)

                println("From : " + calculationPeriodFrom)
                println("To : " +calculationPeriodTo)

                if(inputCheck1.isChecked) {
                    println("Metodo : " + txtCalculationMethod)
                }
                if(inputCheck2.isChecked) {
                    txtSource = inputSource.text.toString()
                }
                println("Source : " + txtSource)
                if(inputCheck3.isChecked) {
                    txtBusinessTarget = inputBusinessTarget.text.toString()
                }
                println("Business : " + txtBusinessTarget)
                if(inputCheck4.isChecked) {
                    //dateNextCalculation = anio.toString()+"-"+mes.toString()+"-"+dia.toString()
                    txtdateNextCalculation = inputDateNextCalculation.text.toString()
                }
                println("Date NExt Calc : " + txtdateNextCalculation)
                if(inputCheck5.isChecked) {
                    txtArea = inputArea.text.toString()
                }
                println("Area : " + txtArea)

                var ocb = OCBUtils()

                ocb.updateKpi(txtID.text.toString(),txtCategoria,txtFrecuencia,inputDescripcion.text.toString(),null,null,null,calculationPeriodFrom,calculationPeriodTo,txtdateNextCalculation,
                        txtCalculationMethod!!,null,inputOrganization.text.toString()!!,inputTitulo.text.toString(),txtSource!!,null,txtBusinessTarget!!,inputFormula.text.toString()!!,null,updateAt,txtArea!!)

                // vuelvo a Acciones
                //val action = EditarIndicadorFragmentDirections.actionEditarIndicadorFragmentToAccionesIndicadorFragment(KpiModel())
                //v.findNavController().navigate(action)

            }
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

    fun cargarForm(indicador : KpiModel? ) {
        txtID.setText(indicador!!.id?.toString())
        inputOrganization.setText(indicador!!.organization?.value?.name?.toString())
        inputTitulo.setText(indicador!!.name?.value?.toString())
        inputDescripcion.setText(indicador!!.description?.value?.toString())
        inputFormula.setText(indicador!!.calculationFormula?.value?.toString())
        txtFrecuencia = indicador!!.calculationFrequency?.value.toString()
        println("Cargo Frec : " + txtFrecuencia)
        txtCategoria = indicador!!.category?.value.toString()
        println("Cargo Cat : " + txtCategoria)
        if(indicador.calculationMethod?.value == null){
            inputCheck1.setChecked(false)
        }else{
            inputCheck1.setChecked(true)
        txtCalculationMethod = indicador!!.calculationMethod?.value.toString()
        println("Cargo Metodo : " + txtCalculationMethod)
        }
        if(indicador.source?.value == null){
            inputCheck2.setChecked(false)
        }else{
            inputCheck2.setChecked(true)
            //inputSource.setText(indicador.source?.value?.toString())
        }
        if(indicador.businessTarget?.value == null){
            inputCheck3.setChecked(false)
        }else{
            inputCheck3.setChecked(true)
            //inputBusinessTarget.setText(indicador.businessTarget?.value?.toString())
        }
        if(indicador.dateNextCalculation?.value == null){
            inputCheck4.setChecked(false)
        }else{
            inputCheck4.setChecked(true)
            //inputDateNextCalculation.setText(indicador.dateNextCalculation?.value?.toString())
        }
        if(indicador.area?.value == null ){
            inputCheck5.setChecked(false)
        }else{
            inputCheck5.setChecked(true)
            //inputArea.setText(indicador.area?.value?.toString())
        }
    }
}