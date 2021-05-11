package edu.ort.visualizar.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

val USERS_COLLECTION_NAME = "users"

@IgnoreExtraProperties
data class UserModel(
        var username: String? = "",
        var password: String? = "",
        var role: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "username" to username,
                "password" to password,
                "role" to role
        )
    }
}