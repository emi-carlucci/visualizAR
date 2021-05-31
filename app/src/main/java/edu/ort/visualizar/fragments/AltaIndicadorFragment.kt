package edu.ort.visualizar.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import edu.ort.visualizar.R
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.utils.DateUtils

class AltaIndicadorFragment : Fragment() {

    lateinit var v : View
    lateinit var txtId : EditText
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
    lateinit var inputValue : EditText
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
    lateinit var btnCheckID : Button
    private val dateUtils = DateUtils()
    var frequencyList = listOf("hourly", "daily", "weekly", "monthly", "yearly", "quarterly", "bimonthly", "biweekly")
    var categoryList = listOf("quantitative", "qualitative", "leading", "lagging", "input", "process", "output", "practical", "directional", "actionable", "financial")
    var calculationMethodList = listOf("manual", "automatic", "semiautomatic")
    var currentStandingList = listOf("very good", "good", "fair", "bad", "very bad")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_alta_indicador, container, false)
        txtId = v.findViewById(R.id.idTextViewValueNew)
        inputOrganization = v.findViewById(R.id.organizationInputTextNew)
        inputDescription = v.findViewById(R.id.descriptionTextInputNew)
        inputLocality = v.findViewById(R.id.localityTextInputNew)
        inputValue = v.findViewById(R.id.valueTextInputNew)
        inputCountry = v.findViewById(R.id.countryTextInputNew)
        inputCalculationPeriodFrom = v.findViewById(R.id.calculatedPeriodFromTextInputNew)
        inputCalculationPeriodTo = v.findViewById(R.id.calculatedPeriodToTextInputNew)
        inputNextCalculationDate = v.findViewById(R.id.nextCalculationDateInputTextNew)
        inputProvider = v.findViewById(R.id.providerInputTextNew)
        inputName = v.findViewById(R.id.nameInputTextNew)
        inputSource = v.findViewById(R.id.sourceTextInputNew)
        inputProduct = v.findViewById(R.id.processTextInputNew)
        inputBusinessTarget = v.findViewById(R.id.businessTargetTextInputNew)
        inputCalculationFormula = v.findViewById(R.id.calculationFormulaTextInputNew)
        inputExpirationDate = v.findViewById(R.id.dateExpiresTextInputNew)
        inputArea = v.findViewById(R.id.areaTextInputNew)
        spFrequency = v.findViewById(R.id.spFrecuenciaNew)
        txtFrequency = frequencyList[0]
        spCategory = v.findViewById(R.id.spCategoriaNew)
        txtCategory = categoryList[0]
        spCalculationMethod = v.findViewById(R.id.spCalculationMethodNew)
        txtCalculationMethod = calculationMethodList[0]
        spStatus = v.findViewById(R.id.spCurrentStandingNew)
        txtStatus = currentStandingList[0]
        btnConfirm = v.findViewById(R.id.btnConfirmarNew)
        btnRestore = v.findViewById(R.id.btnRestoreNew)
        return v
    }

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
        btnCheckID.visibility = View.INVISIBLE

        spFrequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtFrequency = frequencyList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná la frecuencia", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtCategory = categoryList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná la categoría", Snackbar.LENGTH_SHORT).show()
            }
        }
        spCalculationMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtCalculationMethod = calculationMethodList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná el método", Snackbar.LENGTH_SHORT).show()
            }
        }
        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtStatus = currentStandingList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Snackbar.make(v, "Seleccioná el estado actual", Snackbar.LENGTH_SHORT).show()
            }
        }
        btnCheckID.setOnClickListener {
            validateId()
        }
        btnConfirm.setOnClickListener{
            if (isFormValid()) {
                createInOcb()
                resetForm()
                redirectToHome()
            }
        }
        btnRestore.setOnClickListener {
            resetForm()
        }
    }

    private fun isFormValid(): Boolean {
        var isValid = true
        if (txtId.text.toString().isEmpty()) {
            txtId.error = "Por favor, completá el campo"
            isValid = false
        }
        if (inputOrganization.text.toString().isEmpty()) {
            inputOrganization.error = "Por favor, completá el campo"
            isValid = false
        }
        if (inputName.text.toString().isEmpty()) {
            inputName.error = "Por favor, completá el campo"
            isValid = false
        }
        if (inputValue.text.toString().isEmpty()) {
            inputValue.error = "Por favor, completá el campo"
            isValid = false
        }
        if ((inputCalculationPeriodFrom.text.toString().isNotEmpty()) && (!dateUtils.isValidFormat(inputCalculationPeriodFrom.text.toString()))) {
            inputCalculationPeriodFrom.error = "El formato debe ser AAAA-MM-DD"
            isValid = false
        }
        if ((inputCalculationPeriodTo.text.toString().isNotEmpty()) && (!dateUtils.isValidFormat(inputCalculationPeriodTo.text.toString()))) {
            inputCalculationPeriodTo.error = "El formato debe ser AAAA-MM-DD"
            isValid = false
        }
        if ((inputNextCalculationDate.text.toString().isNotEmpty()) && (!dateUtils.isValidFormat(inputNextCalculationDate.text.toString()))) {
            inputNextCalculationDate.error = "El formato debe ser AAAA-MM-DD"
            isValid = false
        }
        if ((inputExpirationDate.text.toString().isNotEmpty()) && (!dateUtils.isValidFormat(inputExpirationDate.text.toString()))) {
            inputExpirationDate.error = "El formato debe ser AAAA-MM-DD"
            isValid = false
        }
        return isValid
    }

    private fun createInOcb() {
        var localInputDescription : String? = null
        var localInputLocality : String? = null
        var localInputCountry : String? = null
        var localInputCalculationPeriodFrom : String? = null
        var localInputCalculationPeriodTo : String? = null
        var localInputNextCalculationDate : String? = null
        var localInputProvider : String? = null
        var localInputSource : String? = null
        var localInputProduct : String? = null
        var localInputBusinessTarget : String? = null
        var localInputCalculationFormula : String? = null
        var localInputExpirationDate : String? = null
        var localInputArea : String? = null
        val localInputId = txtId.text.toString()
        val localInputOrganization = inputOrganization.text.toString()
        val localInputName = inputName.text.toString()
        val localInputValue = inputValue.text.toString()
        val localInputTxtCategory = txtCategory
        val localInputTxtFrequency = txtFrequency
        val localInputTxtStatus = txtStatus
        val localInputTxtCalculationMethod = txtCalculationMethod

        if (inputDescription.text.toString().isNotEmpty()) {
            localInputDescription = inputDescription.text.toString()
        }
        if (inputLocality.text.toString().isNotEmpty()) {
            localInputLocality = inputLocality.text.toString()
        }
        if (inputCountry.text.toString().isNotEmpty()) {
            localInputCountry = inputCountry.text.toString()
        }
        if (inputCalculationPeriodFrom.text.toString().isNotEmpty()) {
            localInputCalculationPeriodFrom = inputCalculationPeriodFrom.text.toString()
        }
        if (inputCalculationPeriodTo.text.toString().isNotEmpty()) {
            localInputCalculationPeriodTo = inputCalculationPeriodTo.text.toString()
        }
        if (inputNextCalculationDate.text.toString().isNotEmpty()) {
            localInputNextCalculationDate = inputNextCalculationDate.text.toString()
        }
        if (inputProvider.text.toString().isNotEmpty()) {
            localInputProvider = inputProvider.text.toString()
        }
        if (inputSource.text.toString().isNotEmpty()) {
            localInputSource = inputSource.text.toString()
        }
        if (inputProduct.text.toString().isNotEmpty()) {
            localInputProduct = inputProduct.text.toString()
        }
        if (inputBusinessTarget.text.toString().isNotEmpty()) {
            localInputBusinessTarget = inputBusinessTarget.text.toString()
        }
        if (inputCalculationFormula.text.toString().isNotEmpty()) {
            localInputCalculationFormula = inputCalculationFormula.text.toString()
        }
        if (inputExpirationDate.text.toString().isNotEmpty()) {
            localInputExpirationDate = inputExpirationDate.text.toString()
        }
        if (inputArea.text.toString().isNotEmpty()) {
            localInputArea = inputArea.text.toString()
        }

        ocbUtils.createKpi(
                localInputId,
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
                localInputValue,
                localInputName,
                localInputSource,
                localInputProduct,
                localInputBusinessTarget,
                localInputCalculationFormula,
                localInputExpirationDate,
                null,
                localInputArea)
    }

    private fun populateSpinner (spinner: Spinner, list : List<String>, context : Context) {
        val adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun resetForm() {
        btnCheckID.visibility = View.INVISIBLE
        spFrequency.setSelection(0, false)
        spCategory.setSelection(0, false)
        spCalculationMethod.setSelection(0, false)
        spStatus.setSelection(0, false)
        txtId.error = null
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
        inputValue.error = null
        txtId.text = null
        inputOrganization.text = null
        inputDescription.text = null
        inputLocality.text = null
        inputCountry.text = null
        inputCalculationPeriodFrom.text = null
        inputCalculationPeriodTo.text = null
        inputNextCalculationDate.text = null
        inputProvider.text = null
        inputName.text = null
        inputSource.text = null
        inputProduct.text = null
        inputBusinessTarget.text = null
        inputCalculationFormula.text = null
        inputExpirationDate.text = null
        inputArea.text = null
        inputValue.text = null
    }

    private fun redirectToHome(){
        val action = AltaIndicadorFragmentDirections.actionAltaIndicadorFragmentToHomeFragment()
        v.findNavController().navigate(action)
        v.findNavController().popBackStack(R.id.altaIndicadorFragment, true)
    }

    private fun validateId() : Boolean {
        var result = true
        btnCheckID.visibility = View.INVISIBLE
        txtId.error = null
        if (txtId.text.toString().isEmpty()){
            result = false
            txtId.error = "Por favor, ingresá el ID"
        } else {
            val getKpi = ocbUtils.getKpi(txtId.text.toString())
            if (getKpi != null) {
                btnCheckID.visibility = View.VISIBLE
                result = false
            }
        }
        return result
    }
}