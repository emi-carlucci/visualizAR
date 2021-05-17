package edu.ort.visualizar.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ort.visualizar.models.*
import edu.ort.visualizar.models.Address
import edu.ort.visualizar.models.Process
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class OCBUtils {

    private val configName = "OCB"
    private val getMethod = "GET"
    private val postMethod = "POST"
    private val patchMethod = "PATCH"
    private val deleteMethod = "DELETE"
    private val applicationType = "application/json; charset=utf-8"
    private val config = DBUtils().getConfig(configName)
    private val url = config?.host + ":" + config?.port + "/" + config?.path

    private fun makeRequest(url: String, method: String, body: KpiModel? = null): String? {
        val client = OkHttpClient()
        var bodyRequest: RequestBody? = null
        var responseString: String? = null
        if (body != null){
            val bodyJson = Gson().toJson(body)
            bodyRequest = RequestBody.create(MediaType.parse(applicationType), bodyJson)
        }
        val request = Request.Builder().method(method, bodyRequest).url(url).build()
        val countDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                countDownLatch.countDown()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected OCB response code ${response.code()}")
                    }
                    responseString = response.body()?.string()
                }
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return responseString
    }

    private fun buildKpiBody(id: String? = null, category: String? = null, calculationFrequency: String? = null, description: String? = null,
                             currentStanding: String? = null, addressLocality: String? = null, addressCountry: String? = null,
                             calculationPeriodFrom: String? = null, calculationPeriodTo: String? = null, dateNextCalculation: String? = null,
                             calculationMethod: String? = null, provider: String? = null, organization: String? = null, kpiValue: String? = null,
                             name: String? = null, source: String? = null, process: String? = null, businessTarget: String? = null,
                             calculationFormula: String? = null, dateExpires: String? = null, updatedAt: String? = null, area: String? = null): KpiModel? {
        val type = "KeyPerformanceIndicator"
        var kpiBody: KpiModel? = null
        var categoryValue: Category? = null
        var calculationFrequencyValue: CalculationFrequency? = null
        var descriptionValue: Description? = null
        var currentStandingValue: CurrentStanding? = null
        var addressValue: Address? = null
        var calculationPeriodValue: CalculationPeriod? = null
        var dateNextCalculationValue: DateNextCalculation? = null
        var calculationMethodValue: CalculationMethod? = null
        var providerValue: Provider? = null
        var organizationValue: Organization? = null
        var kpiValueValue: KpiValue? = null
        var nameValue: Name? = null
        var sourceValue: Source? = null
        var processValue: Process? = null
        var businessTargetValue: BusinessTarget? = null
        var calculationFormulaValue: CalculationFormula? = null
        var dateExpiresValue: DateExpires? = null
        var updatedAtValue: UpdatedAt? = null
        var areaValue: Area? = null
        if (calculationFrequency != null) calculationFrequencyValue = CalculationFrequency(calculationFrequency)
        if (description != null) descriptionValue = Description(description)
        if (currentStanding != null) currentStandingValue = CurrentStanding(currentStanding)
        if (calculationMethod != null) calculationMethodValue = CalculationMethod(calculationMethod)
        if (kpiValue != null) kpiValueValue = KpiValue(kpiValue)
        if (name != null) nameValue = Name(name)
        if (source != null) sourceValue = Source(source)
        if (process != null) processValue = Process(process)
        if (businessTarget != null) businessTargetValue = BusinessTarget(businessTarget)
        if (calculationFormula != null) calculationFormulaValue = CalculationFormula(calculationFormula)
        if (dateExpires != null) dateExpiresValue = DateExpires(dateExpires)
        if (updatedAt != null) updatedAtValue = UpdatedAt(updatedAt)
        if (area != null) areaValue = Area(area)
        if (dateNextCalculation != null) dateNextCalculationValue = DateNextCalculation("DateTime", dateNextCalculation)
        if (provider != null) {
            val providerData = ProviderValue(provider)
            providerValue = Provider(providerData)
        }
        if (organization != null) {
            val organizationData = OrganizationValue(organization)
            organizationValue = Organization(organizationData)
        }
        if (category != null) {
            val categoryList: List<String> = listOf(category)
            categoryValue = Category(categoryList)
        }
        if (calculationPeriodFrom != null && calculationPeriodTo != null) {
            val calculationPeriodData = CalculationPeriodValue(calculationPeriodTo, calculationPeriodFrom)
            calculationPeriodValue = CalculationPeriod(calculationPeriodData)
        }
        if (addressLocality != null && addressCountry != null) {
            val addressData = AddressValue(addressLocality, addressCountry)
            addressValue = Address("PostalAddress", addressData)
        }
        kpiBody = KpiModel(id, type, categoryValue, null, calculationFrequencyValue, descriptionValue, currentStandingValue,
                addressValue, calculationPeriodValue, dateNextCalculationValue, calculationMethodValue, providerValue, organizationValue,
                kpiValueValue, nameValue, sourceValue, processValue, businessTargetValue, calculationFormulaValue, null,
                dateExpiresValue, updatedAtValue, areaValue)
        return kpiBody
    }

    fun getKpiList(): List<KpiModel>? {
        var kpiList: List<KpiModel>? = null
        val response = makeRequest(url, getMethod)
        if (response != null ){
            val listKpiType = object : TypeToken<List<KpiModel>>() {}.type
            kpiList = Gson().fromJson(response, listKpiType)
        }
        return kpiList
    }

    fun getKpi(id: String): KpiModel? {
        var kpi: KpiModel? = null
        val getKpiUrl = "$url/$id"
        val response = makeRequest(getKpiUrl, getMethod)
        if (response != null ){
            val kpiType = object : TypeToken<KpiModel>() {}.type
            kpi = Gson().fromJson(response, kpiType)
        }
        return kpi
    }

    fun updateKpiValue(id: String, value: String): Boolean? {
        var result = false
        val updateKpiValueUrl = "$url/$id/attrs"
        val kpiValueModel = KpiValue(value)
        val updateKpiValueModel = KpiModel(kpiValue = kpiValueModel)
        val response = makeRequest(updateKpiValueUrl, patchMethod, updateKpiValueModel)
        if (response != null && response == "") result = true
        return result
    }

    fun deleteKpi(id: String): Boolean? {
        var result = false
        val deleteKpiUrl = "$url/$id"
        val response = makeRequest(deleteKpiUrl, deleteMethod)
        if (response != null && response == "") result = true
        return result
    }

    fun createKpi(id: String, category: String, calculationFrequency: String, description: String? = null,
                  currentStanding: String? = null, addressLocality: String? = null, addressCountry: String? = null,
                  calculationPeriodFrom: String? = null, calculationPeriodTo: String? = null,dateNextCalculation: String? = null,
                  calculationMethod: String? = null, provider: String? = null, organization: String, kpiValue: String, name: String,
                  source: String? = null, process: String? = null, businessTarget: String? = null, calculationFormula: String? = null,
                  dateExpires: String? = null, updatedAt: String? = null, area: String? = null): Boolean? {
        var result = false
        val createKpiModel = buildKpiBody(id, category, calculationFrequency, description, currentStanding, addressLocality,
                addressCountry, calculationPeriodFrom, calculationPeriodTo, dateNextCalculation, calculationMethod, provider,
                organization, kpiValue, name, source, process, businessTarget, calculationFormula, dateExpires, updatedAt, area)
        val response = makeRequest(url, postMethod, createKpiModel)
        if (response != null && response == "") result = true
        return result
    }

    fun updateKpi(id: String, category: String? = null, calculationFrequency: String? = null, description: String? = null,
                  currentStanding: String? = null, addressLocality: String? = null, addressCountry: String? = null,
                  calculationPeriodFrom: String? = null, calculationPeriodTo: String? = null, dateNextCalculation: String? = null,
                  calculationMethod: String? = null, provider: String? = null, organization: String? = null, kpiValue: String? = null,
                  name: String? = null, source: String? = null, process: String? = null, businessTarget: String? = null,
                  calculationFormula: String? = null, dateExpires: String? = null, updatedAt: String? = null, area: String? = null): Boolean? {
        var result = false
        val updateKpiUrl = "$url/$id/attrs"
        val updateKpiModel = buildKpiBody(null, category, calculationFrequency, description, currentStanding,
                addressLocality, addressCountry, calculationPeriodFrom, calculationPeriodTo, dateNextCalculation, calculationMethod,
                provider, organization, kpiValue, name, source, process, businessTarget, calculationFormula, dateExpires, updatedAt, area)
        val response = makeRequest(updateKpiUrl, patchMethod, updateKpiModel)
        if (response != null && response == "") result = true
        return result
    }

}