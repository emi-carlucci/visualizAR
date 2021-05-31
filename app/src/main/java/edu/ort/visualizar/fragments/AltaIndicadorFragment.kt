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
    lateinit var txtID : String
    lateinit var txtCheckResult : TextView
    lateinit var btnCheckID : Button
    lateinit var inputTitulo : TextView
    lateinit var txtTitulo : String
    lateinit var inputDescripcion : TextView
    lateinit var txtDescripcion : String
    lateinit var inputFormula : TextView
    lateinit var txtFormula : String
    lateinit var spFrecuencia : Spinner
    lateinit var txtFrecuencia : String
    lateinit var spCategoria : Spinner
    lateinit var txtCategoria : String
    lateinit var spCalculationMethod : Spinner
    lateinit var txtCalculationMethod : String
    lateinit var inputSource : TextView
    lateinit var txtSource : String
    lateinit var inputBusinessTarget : TextView
    lateinit var txtBusinessTarget : String
    lateinit var inputDateNextCalculation : TextView
    lateinit var txtDateNextCalculation : String
    lateinit var inputArea : TextView
    lateinit var txtArea : String
    lateinit var btnConfirm : Button
    lateinit var btnLimpiar : Button

    var listaFrecuencia = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var listaCategoria = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
    var listaCalculationMethod = listOf("manual", "automatic", "semiautomatic")

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
        inputID  = v.findViewById(R.id.showMeID)
        txtCheckResult = v.findViewById(R.id.textCheckResult)
        btnCheckID = v.findViewById(R.id.btnAltaCargarKpi)
        inputTitulo = v.findViewById(R.id.inputTitulo)
        inputDescripcion = v.findViewById(R.id.inputDescripcion)
        inputFormula = v.findViewById(R.id.inputFormula)
        spFrecuencia = v.findViewById(R.id.spFrecuenciaNew)
        txtFrecuencia = listaFrecuencia[0]
        spCategoria = v.findViewById(R.id.spCategoriaNew)
        txtCategoria = listaCategoria[0]
        spCalculationMethod = v.findViewById(R.id.spCalculationMethodNew)
        txtCalculationMethod = listaCalculationMethod[0]
        inputSource = v.findViewById(R.id.txtSource)
        inputBusinessTarget = v.findViewById(R.id.inputTarget)
        inputDateNextCalculation = v.findViewById(R.id.inputDateNextCalculation)
        inputArea = v.findViewById(R.id.inputArea)
        btnConfirm = v.findViewById(R.id.btnConfirmarNew)
        btnLimpiar = v.findViewById(R.id.btnRestoreNew)

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

        spCalculationMethod.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaCalculationMethod[position], Snackbar.LENGTH_SHORT).show()
                txtCalculationMethod = listaCalculationMethod[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccione el método", Snackbar.LENGTH_SHORT).show()
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
                txtID = inputID.text.toString()
                println(txtID)
                txtTitulo = inputTitulo.text.toString()
                println(txtTitulo)
                txtDescripcion = inputDescripcion.text.toString()
                println(txtDescripcion)
                txtFormula = inputFormula.text.toString()
                println(txtFormula)
                println("Frec : " + txtFrecuencia)
                println("Categ : " + txtCategoria)
                println("Método : " + txtCalculationMethod)
                println("From : " + calculationPeriodFrom)
                println("To : " + calculationPeriodTo)
                txtSource = inputSource.text.toString()
                println(txtSource)
                txtBusinessTarget = inputBusinessTarget.text.toString()
                println(txtBusinessTarget)
                txtDateNextCalculation = anio.toString()+"-"+mes.toString()+"-"+dia.toString()
                println(txtDateNextCalculation)
                txtArea = inputArea.text.toString()
                println(txtArea)

                var ocb = OCBUtils()

                ocb.createKpi(inputID.text.toString(),txtCategoria,txtFrecuencia,inputDescripcion.text.toString(),null,null,null,calculationPeriodFrom,calculationPeriodTo,txtDateNextCalculation,
                        txtCalculationMethod!!,null,"Ciudades Futuras","",inputTitulo.text.toString(),txtSource!!,null,txtBusinessTarget!!,inputFormula.text.toString()!!,null,null, txtArea!!)

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