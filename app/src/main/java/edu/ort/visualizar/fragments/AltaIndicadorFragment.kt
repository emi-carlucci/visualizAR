package edu.ort.visualizar.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
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
    lateinit var spFrec : Spinner
    lateinit var txtFrec : String
    lateinit var spCat : Spinner
    lateinit var txtCat : String
    lateinit var inputCheck1 : CheckBox
    lateinit var calculationMethod : String
    lateinit var inputCheck2 : CheckBox
    lateinit var source : String
    lateinit var inputCheck3 : CheckBox
    lateinit var businessTarget : String
    lateinit var inputCheck4 : CheckBox
    lateinit var dateNextCalculation : String
    lateinit var inputCheck5 : CheckBox
    lateinit var address : String
    lateinit var inputCheck6 : CheckBox
    lateinit var area : String
    lateinit var btnConfirm : Button
    lateinit var btnLimpiar : Button

    var listaFrec = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var listaCat = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
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
        spFrec = v.findViewById(R.id.spAltaFrecuencia)
        txtFrec = ""
        spCat = v.findViewById(R.id.spAltaCategoria)
        txtCat = ""
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

        populateSpinner(spFrec,listaFrec,requireContext())
        populateSpinner(spCat,listaCat,requireContext())

        spFrec.setSelection(0, false) // evita la primer falsa entrada si existe validación
        spCat.setSelection(0, false) // evita la primer falsa entrada si existe validación

        spFrec.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaFrec[position], Snackbar.LENGTH_SHORT).show()
                txtFrec = listaFrec[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "No hay nada seleccionado", Snackbar.LENGTH_SHORT).show()
            }
        })

        spCat.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(v, listaCat[position], Snackbar.LENGTH_SHORT).show()
                txtCat = listaCat[position]
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
                // Aca se persiste en la BD
                println("")
                println("GRABANDO....")
                println(inputID.text.toString())
                println(inputTitulo.text)
                println(inputDescripcion.text)
                println(inputFormula.text)
                println(txtFrec)
                println(txtCat)

                calculationMethod =""
                source = ""
                businessTarget = ""
                dateNextCalculation =""
                address = ""
                area = ""

                if(inputCheck1.isChecked) {
                    println(inputCheck1.text)
                    calculationMethod ="calculationMethod"
                }
                if(inputCheck2.isChecked) {
                    println(inputCheck2.text)
                    source = "source"
                }
                if(inputCheck3.isChecked) {
                    println(inputCheck3.text)
                    businessTarget = "businessTarget"
                }
                if(inputCheck4.isChecked) {
                    println(inputCheck4.text)
                    dateNextCalculation ="dateNextCalculation"
                }
                if(inputCheck5.isChecked) {
                    println(inputCheck5.text)
                    address = "address"
                }
                if(inputCheck6.isChecked) {
                    println(inputCheck6.text)
                    area = "area"
                }

                var ocb = OCBUtils()

                ocb.createKpi(inputID.text.toString(),txtCat,txtFrec,inputDescripcion.text.toString(),null,null,address!!,null,null,null,
                calculationMethod!!,null,"Ciudades Futuras","kpiValue",inputTitulo.text.toString(),source!!,null,businessTarget!!,inputFormula.text.toString()!!,null,null, area!!)

                //TODO

                //println("vuelvo a HOME")

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