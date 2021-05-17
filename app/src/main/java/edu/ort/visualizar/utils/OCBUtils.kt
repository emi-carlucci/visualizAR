package edu.ort.visualizar.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ort.visualizar.models.*
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

    private fun buildKpiBody(id: String? = null, category: String? = null, calculationFrequency: String? = null,
                             description: String? = null, currentStanding: String? = null, address: String? = null,
                             calculationPeriod: String? = null, dateNextCalculation: String? = null, calculationMethod: String? = null,
                             provider: String? = null, organization: String? = null, kpiValue: String? = null, name: String? = null,
                             source: String? = null, process: String? = null, businessTarget: String? = null, calculationFormula: String? = null,
                             dateExpires: String? = null, updatedAt: String? = null, area: String? = null): KpiModel? {
        var categoryValue: Category? = null
        var calculationFrequencyValue: CalculationFrequency? = null
        if (category != null) {
            val categoryList: List<String> = listOf(category)
            categoryValue = Category(categoryList)
        }
        if (calculationFrequency != null) {
            calculationFrequencyValue = CalculationFrequency(calculationFrequency)
        }


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
                  currentStanding: String? = null, address: String? = null, calculationPeriod: String? = null,
                  dateNextCalculation: String? = null, calculationMethod: String? = null, provider: String? = null,
                  organization: String, kpiValue: String, name: String, source: String? = null,
                  process: String? = null, businessTarget: String? = null, calculationFormula: String? = null,
                  dateExpires: String? = null, updatedAt: String? = null, area: String? = null): Boolean? {
        var result = false
        val createKpiModel = buildKpiBody(id, category, calculationFrequency, description, currentStanding, address,
                calculationPeriod, dateNextCalculation, calculationMethod, provider, organization, kpiValue,
                name, source, process, businessTarget, calculationFormula, dateExpires, updatedAt, area)
        val response = makeRequest(url, postMethod, createKpiModel)
        if (response != null && response == "") result = true
        return result
    }

    fun updateKpi(id: String, category: String? = null, calculationFrequency: String? = null, description: String? = null,
                  currentStanding: String? = null, address: String? = null, calculationPeriod: String? = null,
                  dateNextCalculation: String? = null, calculationMethod: String? = null, provider: String? = null,
                  organization: String? = null, kpiValue: String? = null, name: String? = null, source: String? = null,
                  process: String? = null, businessTarget: String? = null, calculationFormula: String? = null,
                  dateExpires: String? = null, updatedAt: String? = null, area: String? = null): Boolean? {
        var result = false
        val updateKpiUrl = "$url/$id/attrs"
        val updateKpiModel = buildKpiBody(null, category, calculationFrequency, description, currentStanding, address,
                calculationPeriod, dateNextCalculation, calculationMethod, provider, organization, kpiValue,
                name, source, process, businessTarget, calculationFormula, dateExpires, updatedAt, area)
        val response = makeRequest(updateKpiUrl, patchMethod, updateKpiModel)
        if (response != null && response == "") result = true
        return result
    }

}