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
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import edu.ort.visualizar.R
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.models.KpiModel
import edu.ort.visualizar.utils.DateUtils


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
    lateinit var txtPageTitle : TextView
    lateinit var indicator: KpiModel
    private val notDefinedText : String = "NO DEFINIDO"
    private val dateUtils = DateUtils()
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
        txtPageTitle = v.findViewById(R.id.txtEditarIndicador)
        indicator = EditarIndicadorFragmentArgs.fromBundle(requireArguments()).indicadorId?.let { ocbUtils.getKpi(it) }!!
        if (indicator != null) {
            fillForm(indicator)
            val isViewDetails = EditarIndicadorFragmentArgs.fromBundle(requireArguments()).isViewDetails
            if (isViewDetails){
                disableEdition()
            }
        } else {
            Toast.makeText(activity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
            redirectToHome()
        }

        return v
    }

    private fun disableEdition(){
        inputOrganization.isEnabled = false
        inputOrganization.isEnabled = false
        inputDescription.isEnabled = false
        inputLocality.isEnabled = false
        inputCountry.isEnabled = false
        inputCalculationPeriodFrom.isEnabled = false
        inputCalculationPeriodTo.isEnabled = false
        inputNextCalculationDate.isEnabled = false
        inputProvider.isEnabled = false
        inputName.isEnabled = false
        inputSource.isEnabled = false
        inputProduct.isEnabled = false
        inputBusinessTarget.isEnabled = false
        inputCalculationFormula.isEnabled = false
        inputExpirationDate.isEnabled = false
        inputArea.isEnabled = false
        spFrequency.isEnabled = false
        spCategory.isEnabled = false
        spCalculationMethod.isEnabled = false
        spStatus.isEnabled = false
        btnConfirm.isVisible = false
        btnRestore.isVisible = false
        txtPageTitle.text = getString(R.string.view_details_page_title)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        spFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtFrequency = frequencyList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccion?? la frecuencia", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtCategory = categoryList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccion?? la categor??a", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCalculationMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtCalculationMethod = calculationMethodList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccion?? el m??todo", Snackbar.LENGTH_SHORT).show()
            }
        }
        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtStatus = currentStandingList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccion?? el estado actual", Snackbar.LENGTH_SHORT).show()
            }
        }
        btnRestore.setOnClickListener {
            fillForm(indicator)
        }
        btnConfirm.setOnClickListener{
            if (isFormValid()) {
                val result = updateInOcb()
                if (result) {
                    resetFormErrors()
                    redirectToHome()
                } else {
                    Toast.makeText(activity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun redirectToHome(){
        val action = EditarIndicadorFragmentDirections.actionEditarIndicadorFragmentToAccionesIndicadorFragment(indicator)
        v.findNavController().navigate(action)
        v.findNavController().popBackStack(R.id.editarIndicadorFragment, true)
    }

    private fun updateInOcb() : Boolean {
        val result: Boolean
        var localInputOrganization : String? = null
        var localInputDescription : String? = null
        var localInputLocality : String? = null
        var localInputCountry : String? = null
        var localInputCalculationPeriodFrom : String? = null
        var localInputCalculationPeriodTo : String? = null
        var localInputNextCalculationDate : String? = null
        var localInputProvider : String? = null
        var localInputName : String? = null
        var localInputSource : String? = null
        var localInputProduct : String? = null
        var localInputBusinessTarget : String? = null
        var localInputCalculationFormula : String? = null
        var localInputExpirationDate : String? = null
        var localInputArea : String? = null
        var localInputTxtFrequency : String? = null
        var localInputTxtCategory : String? = null
        var localInputTxtCalculationMethod : String? = null
        var localInputTxtStatus : String? = null

        if (indicator.category != null) {
            localInputTxtCategory = txtCategory
        }
        if (indicator.calculationFrequency != null) {
            localInputTxtFrequency = txtFrequency
        }
        if (indicator.description != null) {
            localInputDescription = inputDescription.text.toString()
        }
        if (indicator.currentStanding != null) {
            localInputTxtStatus = txtStatus
        }
        if (indicator.address != null) {
            localInputLocality = inputLocality.text.toString()
            localInputCountry = inputCountry.text.toString()
        }
        if (indicator.calculationPeriod != null) {
            localInputCalculationPeriodFrom = inputCalculationPeriodFrom.text.toString()
            localInputCalculationPeriodTo = inputCalculationPeriodTo.text.toString()
        }
        if (indicator.dateNextCalculation != null) {
            localInputNextCalculationDate = inputNextCalculationDate.text.toString()
        }
        if (indicator.calculationMethod != null) {
            localInputTxtCalculationMethod = txtCalculationMethod
        }
        if (indicator.provider != null) {
            localInputProvider = inputProvider.text.toString()
        }
        if (indicator.organization != null) {
            localInputOrganization = inputOrganization.text.toString()
        }
        if (indicator.name != null) {
            localInputName = inputName.text.toString()
        }
        if (indicator.source != null) {
            localInputSource = inputSource.text.toString()
        }
        if (indicator.process != null) {
            localInputProduct = inputProduct.text.toString()
        }
        if (indicator.businessTarget != null) {
            localInputBusinessTarget = inputBusinessTarget.text.toString()
        }
        if (indicator.calculationFormula != null) {
            localInputCalculationFormula = inputCalculationFormula.text.toString()
        }
        if (indicator.dateExpires != null) {
            localInputExpirationDate = inputExpirationDate.text.toString()
        }
        if (indicator.area != null) {
            localInputArea = inputArea.text.toString()
        }
        result = ocbUtils.updateKpi(
                 indicator.id.toString(),
                 localInputTxtCategory,
                 localInputTxtFrequency,
                 localInputDescription,
                 localInputTxtStatus,
                 localInputLocality,
                 localInputCountry,
                 localInputCalculationPeriodFrom,
                 localInputCalculationPeriodTo,
                 localInputNextCalculationDate,
                 localInputTxtCalculationMethod,
                 localInputProvider,
                 localInputOrganization,
                 localInputName,
                 localInputSource,
                 localInputProduct,
                 localInputBusinessTarget,
                 localInputCalculationFormula,
                 localInputExpirationDate,
                 null,
                 localInputArea)
        return result
    }

    private fun isFormValid(): Boolean {
        var isValid = true
        if (inputOrganization.text.toString().isEmpty()) {
            inputOrganization.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputOrganization.text.toString().contains("=")) {
            inputOrganization.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputDescription.text.toString().isEmpty()) {
            inputDescription.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputDescription.text.toString().contains("=")) {
            inputDescription.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputLocality.text.toString().isEmpty()) {
            inputLocality.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputLocality.text.toString().contains("=")) {
            inputLocality.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputCountry.text.toString().isEmpty()) {
            inputCountry.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputCountry.text.toString().contains("=")) {
            inputCountry.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputProvider.text.toString().isEmpty()) {
            inputProvider.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputProvider.text.toString().contains("=")) {
            inputProvider.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputName.text.toString().isEmpty()) {
            inputName.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputName.text.toString().contains("=")) {
            inputName.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputSource.text.toString().isEmpty()) {
            inputSource.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputSource.text.toString().contains("=")) {
            inputSource.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputProduct.text.toString().isEmpty()) {
            inputProduct.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputProduct.text.toString().contains("=")) {
            inputProduct.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputBusinessTarget.text.toString().isEmpty()) {
            inputBusinessTarget.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputBusinessTarget.text.toString().contains("=")) {
            inputBusinessTarget.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputCalculationFormula.text.toString().isEmpty()) {
            inputCalculationFormula.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputCalculationFormula.text.toString().contains("=")) {
            inputCalculationFormula.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputArea.text.toString().isEmpty()) {
            inputArea.error = getString(R.string.completeInput)
            isValid = false
        }
        if (inputArea.text.toString().contains("=")) {
            inputArea.error = getString(R.string.invalidEqualChar)
            isValid = false
        }
        if (inputCalculationPeriodFrom.text.toString().isEmpty()) {
            inputCalculationPeriodFrom.error = getString(R.string.completeInput)
            isValid = false
        } else {
            if ((!dateUtils.isValidFormat(inputCalculationPeriodFrom.text.toString())) && (inputCalculationPeriodFrom.text.toString() != notDefinedText)) {
                inputCalculationPeriodFrom.error = getString(R.string.invalidDate)
                isValid = false
            }
        }
        if (inputCalculationPeriodTo.text.toString().isEmpty()) {
            inputCalculationPeriodTo.error = getString(R.string.completeInput)
            isValid = false
        } else {
            if ((!dateUtils.isValidFormat(inputCalculationPeriodTo.text.toString())) && (inputCalculationPeriodTo.text.toString() != notDefinedText)) {
                inputCalculationPeriodTo.error = getString(R.string.invalidDate)
                isValid = false
            }
        }
        if (inputNextCalculationDate.text.toString().isEmpty()) {
            inputNextCalculationDate.error = getString(R.string.completeInput)
            isValid = false
        } else {
            if ((!dateUtils.isValidFormat(inputNextCalculationDate.text.toString())) && (inputNextCalculationDate.text.toString() != notDefinedText)) {
                inputNextCalculationDate.error = getString(R.string.invalidDate)
                isValid = false
            }
        }
        if (inputExpirationDate.text.toString().isEmpty()) {
            inputExpirationDate.error = getString(R.string.completeInput)
            isValid = false
        } else {
            if ((!dateUtils.isValidFormat(inputExpirationDate.text.toString())) && (inputExpirationDate.text.toString() != notDefinedText)) {
                inputExpirationDate.error = getString(R.string.invalidDate)
                isValid = false
            }
        }
        return isValid
    }

    private fun resetFormErrors() {
        inputOrganization.error = null
        inputDescription.error = null
        inputLocality.error = null
        inputCountry.error = null
        inputCalculationPeriodFrom.error = null
        inputCalculationPeriodTo.error = null
        inputNextCalculationDate.error = null
        inputProvider.error = null
        inputName.error = null
        inputSource.error = null
        inputProduct.error = null
        inputBusinessTarget.error = null
        inputCalculationFormula.error = null
        inputExpirationDate.error = null
        inputArea.error = null
    }

    private fun populateSpinner (spinner: Spinner, list : List<String>, context : Context) {
        val adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun getSpinnerPosition (list: String, value: String): Int {
        var position = 0
        if (list == "frequencyList") {
            frequencyList.forEachIndexed { index, element ->
                if (element == value){
                    position = index
                }
            }
        } else {
            if (list == "categoryList") {
                categoryList.forEachIndexed { index, element ->
                    if (element == value){
                        position = index
                    }
                }
            } else {
                if (list == "calculationMethodList") {
                    calculationMethodList.forEachIndexed { index, element ->
                        if (element == value){
                            position = index
                        }
                    }
                } else {
                    if (list == "currentStandingList") {
                        currentStandingList.forEachIndexed { index, element ->
                            if (element == value){
                                position = index
                            }
                        }
                    }
                }
            }
        }
        return position
    }

    private fun fillForm(kpiIndicator : KpiModel) {
        populateSpinner(spFrequency, frequencyList, requireContext())
        populateSpinner(spCategory, categoryList, requireContext())
        populateSpinner(spCalculationMethod, calculationMethodList, requireContext())
        populateSpinner(spStatus, currentStandingList, requireContext())

        resetFormErrors()

        if (kpiIndicator.id == null){
            txtId.text = notDefinedText
        } else {
            txtId.text = kpiIndicator.id.toString()
        }
        if (kpiIndicator.organization == null){
            inputOrganization.setText(notDefinedText)
            inputOrganization.isEnabled = false
        } else {
            inputOrganization.setText(kpiIndicator.organization!!.value?.name.toString())
        }
        if (kpiIndicator.description == null){
            inputDescription.setText(notDefinedText)
            inputDescription.isEnabled = false
        } else {
            inputDescription.setText(kpiIndicator.description!!.value.toString())
        }
        if (kpiIndicator.address == null){
            inputLocality.setText(notDefinedText)
            inputCountry.setText(notDefinedText)
            inputLocality.isEnabled = false
            inputCountry.isEnabled = false
        } else {
            if (kpiIndicator.address!!.value?.addressLocality == null){
                inputLocality.setText(notDefinedText)
                inputLocality.isEnabled = false
            } else {
                inputLocality.setText(kpiIndicator.address!!.value?.addressLocality.toString())
            }
            if (kpiIndicator.address!!.value?.addressCountry == null){
                inputCountry.setText(notDefinedText)
                inputCountry.isEnabled = false
            } else {
                inputCountry.setText(kpiIndicator.address!!.value?.addressCountry.toString())
            }
        }
        if (kpiIndicator.calculationPeriod == null){
            inputCalculationPeriodFrom.setText(notDefinedText)
            inputCalculationPeriodTo.setText(notDefinedText)
            inputCalculationPeriodFrom.isEnabled = false
            inputCalculationPeriodTo.isEnabled = false
        } else {
            if (kpiIndicator.calculationPeriod!!.value?.from == null){
                inputCalculationPeriodFrom.setText(notDefinedText)
                inputCalculationPeriodFrom.isEnabled = false
            } else {
                val parsedCalculationPeriodFrom = (kpiIndicator.calculationPeriod!!.value?.from.toString()).split("T")
                inputCalculationPeriodFrom.setText(parsedCalculationPeriodFrom[0])
            }
            if (kpiIndicator.calculationPeriod!!.value?.to == null){
                inputCalculationPeriodTo.setText(notDefinedText)
                inputCalculationPeriodTo.isEnabled = false
            } else {
                val parsedCalculationPeriodTo = (kpiIndicator.calculationPeriod!!.value?.to.toString()).split("T")
                inputCalculationPeriodTo.setText(parsedCalculationPeriodTo[0])
            }
        }
        if (kpiIndicator.dateNextCalculation == null){
            inputNextCalculationDate.setText(notDefinedText)
            inputNextCalculationDate.isEnabled = false
        } else {
            val parsedNextCalculationDate = (kpiIndicator.dateNextCalculation!!.value.toString()).split("T")
            inputNextCalculationDate.setText(parsedNextCalculationDate[0])
        }
        if (kpiIndicator.provider == null){
            inputProvider.setText(notDefinedText)
            inputProvider.isEnabled = false
        } else {
            inputProvider.setText(kpiIndicator.provider!!.value?.name.toString())
        }
        if (kpiIndicator.name == null){
            inputName.setText(notDefinedText)
            inputName.isEnabled = false
        } else {
            inputName.setText(kpiIndicator.name!!.value.toString())
        }
        if (kpiIndicator.source == null){
            inputSource.setText(notDefinedText)
            inputSource.isEnabled = false
        } else {
            inputSource.setText(kpiIndicator.source!!.value.toString())
        }
        if (kpiIndicator.process == null){
            inputProduct.setText(notDefinedText)
            inputProduct.isEnabled = false
        } else {
            inputProduct.setText(kpiIndicator.process!!.value.toString())
        }
        if (kpiIndicator.businessTarget == null){
            inputBusinessTarget.setText(notDefinedText)
            inputBusinessTarget.isEnabled = false
        } else {
            inputBusinessTarget.setText(kpiIndicator.businessTarget!!.value.toString())
        }
        if (kpiIndicator.calculationFormula == null){
            inputCalculationFormula.setText(notDefinedText)
            inputCalculationFormula.isEnabled = false
        } else {
            inputCalculationFormula.setText(kpiIndicator.calculationFormula!!.value.toString())
        }
        if (kpiIndicator.dateExpires == null){
            inputExpirationDate.setText(notDefinedText)
            inputExpirationDate.isEnabled = false
        } else {
            val parsedExpirationDate = (kpiIndicator.dateExpires!!.value.toString()).split("T")
            inputExpirationDate.setText(parsedExpirationDate[0])
        }
        if (kpiIndicator.area == null){
            inputArea.setText(notDefinedText)
            inputArea.isEnabled = false
        } else {
            inputArea.setText(kpiIndicator.area!!.value.toString())
        }
        if (kpiIndicator.calculationFrequency == null){
            spFrequency.setSelection(0, false)
            spFrequency.isEnabled = false
        } else {
            val posFrequency = getSpinnerPosition("frequencyList", kpiIndicator.calculationFrequency!!.value.toString())
            spFrequency.setSelection(posFrequency, false)
        }
        if (kpiIndicator.category == null){
            spCategory.setSelection(0, false)
            spCategory.isEnabled = false
        } else {
            val posCategory = kpiIndicator.category!!.value?.get(0)?.let { getSpinnerPosition("categoryList", it) }
            if (posCategory != null) {
                spCategory.setSelection(posCategory, false)
            } else {
                spCategory.setSelection(0, false)
                spCategory.isEnabled = false
            }
        }
        if (kpiIndicator.calculationMethod == null){
            spCalculationMethod.setSelection(0, false)
            spCalculationMethod.isEnabled = false
        } else {
            val posCalculationMethod = getSpinnerPosition("calculationMethodList", kpiIndicator.calculationMethod!!.value.toString())
            spCalculationMethod.setSelection(posCalculationMethod, false)
        }
        if (kpiIndicator.currentStanding == null){
            spStatus.setSelection(0, false)
            spStatus.isEnabled = false
        } else {
            val posStatus = getSpinnerPosition("currentStandingList", kpiIndicator.currentStanding!!.value.toString())
            spStatus.setSelection(posStatus, false)
        }
    }
}