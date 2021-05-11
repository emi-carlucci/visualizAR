package edu.ort.visualizar.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(
        var username: String? = "",
        var password: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "username" to username,
                "password" to password
        )
    }
}