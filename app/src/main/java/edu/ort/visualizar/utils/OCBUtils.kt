package edu.ort.visualizar.utils

import com.google.gson.Gson
import edu.ort.visualizar.models.KpiModel
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class OCBUtils {

    private val configName = "OCB"
    private val config = DBUtils().getConfig(configName)
    private val url = config?.host + ":" + config?.port + "/" + config?.path

    private fun makeRequest(url: String, method: String, body: String? = null): KpiModel? {
        val client = OkHttpClient()
        var bodyRequest: RequestBody? = null
        var responseModel: KpiModel? = null
        if (body != null){
            bodyRequest = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
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
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    responseModel = Gson().fromJson(response.body()?.string(), KpiModel::class.java)
                }
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return responseModel
    }

    fun getKpiList(): KpiModel? {
        var kpiList = makeRequest(url, "GET")
        return kpiList
    }

}