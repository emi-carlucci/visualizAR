package edu.ort.visualizar.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import edu.ort.visualizar.R
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.models.KpiModel


class EditarIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var txtId : TextView
    lateinit var inputOrganization : EditText
    lateinit var inputDescription : EditText
    lateinit var inputLocality : EditText
    lateinit var inputCountry : EditText
    lateinit var inputCalculationPeriodFrom : EditText
    lateinit var inputCalculationPeriodTo : EditText
    lateinit var inputNextCalculationDate : EditText
    lateinit var inputProvider : EditText
    lateinit var inputName : EditText
    lateinit var inputSource : EditText
    lateinit var inputProduct : EditText
    lateinit var inputBusinessTarget : EditText
    lateinit var inputCalculationFormula : EditText
    lateinit var inputExpirationDate : EditText
    lateinit var inputArea : EditText
    lateinit var spCalculationMethod : Spinner
    lateinit var spFrequency : Spinner
    lateinit var spCategory : Spinner
    lateinit var spStatus : Spinner
    lateinit var txtCalculationMethod : String
    lateinit var txtCategory : String
    lateinit var txtFrequency : String
    lateinit var txtStatus : String
    lateinit var btnConfirm : Button
    lateinit var btnRestore : Button
    lateinit var indicator: KpiModel
    private var validateAction : Boolean = true
    private val notDefinedText : String = "NO DEFINIDO"
    var frequencyList = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var categoryList = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
    var calculationMethodList = listOf("manual", "automatic", "semiautomatic")
    var currentStandingList = listOf("very good", "good", "fair", "bad", "very bad")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_editar_indicador, container, false)
        txtId = v.findViewById(R.id.idTextViewValue)
        inputOrganization = v.findViewById(R.id.organizationInputText)
        inputDescription = v.findViewById(R.id.descriptionTextInput)
        inputLocality = v.findViewById(R.id.localityTextInput)
        inputCountry = v.findViewById(R.id.countryTextInput)
        inputCalculationPeriodFrom = v.findViewById(R.id.calculatedPeriodFromTextInput)
        inputCalculationPeriodTo = v.findViewById(R.id.calculatedPeriodToTextInput)
        inputNextCalculationDate = v.findViewById(R.id.nextCalculationDateInputText)
        inputProvider = v.findViewById(R.id.providerInputText)
        inputName = v.findViewById(R.id.nameInputText)
        inputSource = v.findViewById(R.id.sourceTextInput)
        inputProduct = v.findViewById(R.id.processTextInput)
        inputBusinessTarget = v.findViewById(R.id.businessTargetTextInput)
        inputCalculationFormula = v.findViewById(R.id.calculationFormulaTextInput)
        inputExpirationDate = v.findViewById(R.id.dateExpiresTextInput)
        inputArea = v.findViewById(R.id.areaTextInput)
        spFrequency = v.findViewById(R.id.spFrecuencia)
        txtFrequency = frequencyList[0]
        spCategory = v.findViewById(R.id.spCategoria)
        txtCategory = categoryList[0]
        spCalculationMethod = v.findViewById(R.id.spCalculationMethod)
        txtCalculationMethod = calculationMethodList[0]
        spStatus = v.findViewById(R.id.spCurrentStanding)
        txtStatus = currentStandingList[0]
        btnConfirm = v.findViewById(R.id.btnConfirmar)
        btnRestore = v.findViewById(R.id.btnRestore)
        indicator = EditarIndicadorFragmentArgs.fromBundle(requireArguments()).indicador!!
        fillForm(indicator)
        return v
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        populateSpinner(spFrequency, frequencyList, requireContext())
        populateSpinner(spCategory, categoryList, requireContext())
        populateSpinner(spCalculationMethod, calculationMethodList, requireContext())
        populateSpinner(spStatus, currentStandingList, requireContext())

        spFrequency.setSelection(0, false)
        spCategory.setSelection(0, false)
        spCalculationMethod.setSelection(0, false)
        spStatus.setSelection(0, false)

        spFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(view, frequencyList[position], Snackbar.LENGTH_SHORT).show()
                txtFrequency = frequencyList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná la frecuencia", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(view, categoryList[position], Snackbar.LENGTH_SHORT).show()
                txtCategory = categoryList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná la categoría", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCalculationMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(view, calculationMethodList[position], Snackbar.LENGTH_SHORT).show()
                txtCalculationMethod = calculationMethodList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná el método", Snackbar.LENGTH_SHORT).show()
            }
        }
        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Snackbar.make(view, currentStandingList[position], Snackbar.LENGTH_SHORT).show()
                txtStatus = currentStandingList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná el estado actual", Snackbar.LENGTH_SHORT).show()
            }
        }
        btnRestore.setOnClickListener {
            fillForm(indicator)
        }
        btnConfirm.setOnClickListener{
            if(inputOrganization.text.toString() == "") {
                Snackbar.make(v, "Ingresá la organización", Snackbar.LENGTH_SHORT).show()
                validateAction = false
            }
            if(inputName.text.toString() == "") {
                Snackbar.make(v, "Ingresá el nombre", Snackbar.LENGTH_SHORT).show()
                validateAction = false
            }
            if(inputDescription.text.toString() == ""){
                Snackbar.make(v, "Ingresá la descripción", Snackbar.LENGTH_SHORT).show()
                validateAction = false
            }
            if(inputCalculationFormula.text.toString() == ""){
                Snackbar.make(v, "Ingresá la fórmula de cálculo", Snackbar.LENGTH_SHORT).show()
                validateAction = false
            }
            if (validateAction) {
//                ocbUtils.updateKpi(txtId.text.toString(),txtCategoria,txtFrecuencia,inputDescripcion.text.toString(),null,null,null,calculationPeriodFrom,calculationPeriodTo,txtdateNextCalculation,
//                        txtCalculationMethod!!,null,inputOrganization.text.toString()!!,inputTitulo.text.toString(),txtSource!!,null,txtBusinessTarget!!,inputFormula.text.toString()!!,null,updateAt,txtArea!!)
                val action = EditarIndicadorFragmentDirections.actionEditarIndicadorFragmentToAccionesIndicadorFragment(indicator)
                v.findNavController().navigate(action)
            }
            validateAction = true
        }
    }

    private fun populateSpinner (spinner: Spinner, list : List<String>, context : Context)
    {
        val adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun fillForm(kpiIndicator : KpiModel) {
        if (kpiIndicator.id == null){
            txtId.text = notDefinedText
        } else {
            txtId.text = kpiIndicator.id.toString()
        }
        if (kpiIndicator.organization == null){
            inputOrganization.setText(notDefinedText)
        } else {
            inputOrganization.setText(kpiIndicator.organization!!.value.toString())
        }
        if (kpiIndicator.description == null){
            inputDescription.setText(notDefinedText)
        } else {
            inputDescription.setText(kpiIndicator.description!!.value.toString())
        }
        if (kpiIndicator.address == null){
            inputLocality.setText(notDefinedText)
            inputCountry.setText(notDefinedText)
        } else {
            if (kpiIndicator.address!!.value?.addressLocality == null){
                inputLocality.setText(notDefinedText)
            } else {
                inputLocality.setText(kpiIndicator.address!!.value?.addressLocality.toString())
            }
            if (kpiIndicator.address!!.value?.addressCountry == null){
                inputCountry.setText(notDefinedText)
            } else {
                inputCountry.setText(kpiIndicator.address!!.value?.addressCountry.toString())
            }
        }
        if (kpiIndicator.calculationPeriod == null){
            inputCalculationPeriodFrom.setText(notDefinedText)
            inputCalculationPeriodTo.setText(notDefinedText)
        } else {
            if (kpiIndicator.calculationPeriod!!.value?.from == null){
                inputCalculationPeriodFrom.setText(notDefinedText)
            } else {
                inputCalculationPeriodFrom.setText(kpiIndicator.calculationPeriod!!.value?.from.toString())
            }
            if (kpiIndicator.calculationPeriod!!.value?.to == null){
                inputCalculationPeriodTo.setText(notDefinedText)
            } else {
                inputCalculationPeriodTo.setText(kpiIndicator.calculationPeriod!!.value?.to.toString())
            }
        }
        if (kpiIndicator.dateNextCalculation == null){
            inputNextCalculationDate.setText(notDefinedText)
        } else {
            inputNextCalculationDate.setText(kpiIndicator.dateNextCalculation!!.value.toString())
        }
        if (kpiIndicator.provider == null){
            inputProvider.setText(notDefinedText)
        } else {
            inputProvider.setText(kpiIndicator.provider!!.value?.name.toString())
        }
        if (kpiIndicator.name == null){
            inputName.setText(notDefinedText)
        } else {
            inputName.setText(kpiIndicator.name!!.value.toString())
        }
        if (kpiIndicator.source == null){
            inputSource.setText(notDefinedText)
        } else {
            inputSource.setText(kpiIndicator.source!!.value.toString())
        }
        if (kpiIndicator.process == null){
            inputProduct.setText(notDefinedText)
        } else {
            inputProduct.setText(kpiIndicator.process!!.value.toString())
        }
        if (kpiIndicator.businessTarget == null){
            inputBusinessTarget.setText(notDefinedText)
        } else {
            inputBusinessTarget.setText(kpiIndicator.businessTarget!!.value.toString())
        }
        if (kpiIndicator.calculationFormula == null){
            inputCalculationFormula.setText(notDefinedText)
        } else {
            inputCalculationFormula.setText(kpiIndicator.calculationFormula!!.value.toString())
        }
        if (kpiIndicator.dateExpires == null){
            inputExpirationDate.setText(notDefinedText)
        } else {
            inputExpirationDate.setText(kpiIndicator.dateExpires!!.value.toString())
        }
        if (kpiIndicator.area == null){
            inputArea.setText(notDefinedText)
        } else {
            inputArea.setText(kpiIndicator.area!!.value.toString())
        }
        txtFrequency = if (kpiIndicator.calculationFrequency == null){
            notDefinedText
        } else {
            kpiIndicator.calculationFrequency!!.value.toString()
        }
        txtCategory = if (kpiIndicator.category == null){
            notDefinedText
        } else {
            kpiIndicator.category!!.value?.get(0) ?: notDefinedText
        }
        txtCalculationMethod = if (kpiIndicator.calculationMethod == null){
            notDefinedText
        } else {
            kpiIndicator.calculationMethod!!.value.toString()
        }
        txtStatus = if (kpiIndicator.currentStanding == null){
            notDefinedText
        } else {
            kpiIndicator.currentStanding!!.value.toString()
        }
    }
}