package edu.ort.visualizar.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ConfigModel(
        var name: String? = "",
        var host: String? = "",
        var port: String? = "",
        var path: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name,
                "host" to host,
                "port" to port,
                "path" to path
        )
    }
}