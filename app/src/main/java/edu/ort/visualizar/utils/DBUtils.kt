package edu.ort.visualizar.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import edu.ort.visualizar.models.ConfigModel
import edu.ort.visualizar.models.UserModel


class DBUtils {

    var db: FirebaseFirestore? = null
    var users: CollectionReference? = null
    var configs: CollectionReference? = null
    val USERS_COLLECTION_NAME = "users"
    val CONFIGS_COLLECTION_NAME = "configs"

    constructor() {
        db = FirebaseFirestore.getInstance()
        setCollections()
    }

    private fun setCollections() {
        users = db!!.collection(USERS_COLLECTION_NAME)
        configs = db!!.collection(CONFIGS_COLLECTION_NAME)
    }

    fun getUser(username: String?): UserModel? {
        var user: UserModel? = null
        var task = users?.get()
        if (task != null) { while (!task.isComplete) {} }
        if (task != null && task.isSuccessful) {
            var queryDocumentSnapshots = task.result
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty) {
                var userList = queryDocumentSnapshots.documents
                for (doc in userList) {
                    val getUser = doc.toObject(UserModel::class.java)
                    if (getUser != null && getUser.username == username) {
                        user = getUser
                        break
                    }
                }
            }
        }
        return user
    }

    fun getConfig(configName: String?): ConfigModel? {
        var config: ConfigModel? = null
        var task = configs?.get()
        if (task != null) { while (!task.isComplete) {} }
        if (task != null && task.isSuccessful) {
            var queryDocumentSnapshots = task.result
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty) {
                var configList = queryDocumentSnapshots.documents
                for (conf in configList) {
                    val getConfig = conf.toObject(ConfigModel::class.java)
                    if (getConfig != null && getConfig.name == configName) {
                        config = getConfig
                        break
                    }
                }
            }
        }
        return config
    }

}