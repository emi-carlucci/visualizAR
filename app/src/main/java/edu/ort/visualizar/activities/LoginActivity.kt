package edu.ort.visualizar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import edu.ort.visualizar.R
import edu.ort.visualizar.models.UserModel
import edu.ort.visualizar.models.USERS_COLLECTION_NAME

class LoginActivity : AppCompatActivity() {

    private var signInBtn: Button? = null
    private var userNameTxt: EditText? = null
    private var passwordTxt: EditText? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInBtn = findViewById<View>(R.id.signin_btn) as Button
        progressBar = findViewById<View>(R.id.progress_bar_main) as ProgressBar

        signInBtn!!.setOnClickListener {
            userNameTxt = findViewById<View>(R.id.user_name_txt) as EditText
            passwordTxt = findViewById<View>(R.id.password_txt) as EditText
            val userName = userNameTxt!!.text.toString()
            val password = passwordTxt!!.text.toString()

            if (isUserFormValid(userName, password)) {
                progressBar!!.visibility = View.VISIBLE
                signInBtn!!.visibility = View.GONE
                var db = FirebaseFirestore.getInstance()
                var users = db.collection(USERS_COLLECTION_NAME)
                var task = users.get()

                while (!task.isComplete) {}

                progressBar!!.visibility = View.INVISIBLE
                signInBtn!!.visibility = View.VISIBLE

                if (task.isSuccessful) {
                    var queryDocumentSnapshots = task.getResult()
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty) {
                        var foundUser : UserModel? = null
                        var userList = queryDocumentSnapshots.documents
                        for (doc in userList) {
                            val user = doc.toObject(UserModel::class.java)
                            if (user != null && user.username == userName){
                                foundUser = user
                                break
                            }
                        }
                        if (foundUser != null){
                            if (foundUser.password == password){
                                val loginIntent = Intent(this, MainActivity::class.java).apply {}
                                startActivity(loginIntent)
                            }
                            else {
                                Toast.makeText(this, "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            Toast.makeText(this, "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(this, "Ocurrió un problema. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(this, "Ocurrió un problema. Por favor, intentá nuevamente", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isUserFormValid(userName: String, password: String): Boolean {
        var isValid = true
        if (userName.isEmpty()) {
            userNameTxt!!.error = "Por favor, ingresá tu usuario"
            isValid = false
        } else {
            userNameTxt!!.error = null
        }
        if (password.isEmpty()) {
            passwordTxt!!.error = "Por favor, ingresá una contraseña valida"
            isValid = false
        } else {
            passwordTxt!!.error = null
        }
        return isValid
    }

}