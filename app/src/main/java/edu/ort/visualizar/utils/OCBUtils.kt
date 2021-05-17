package edu.ort.visualizar.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ort.visualizar.models.KpiModel
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

    private fun makeRequest(url: String, method: String, body: String? = null): String? {
        val client = OkHttpClient()
        var bodyRequest: RequestBody? = null
        var responseString: String? = null
        if (body != null){
            bodyRequest = RequestBody.create(MediaType.parse(applicationType), body)
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

    fun getKpiList(): List<KpiModel>? {
        var kpiList: List<KpiModel>? = null
        val response = makeRequest(url, getMethod)
        if (response != null ){
            val listKpiType = object : TypeToken<List<KpiModel>>() {}.type
            kpiList = Gson().fromJson(response, listKpiType)
        }
        return kpiList
    }

}