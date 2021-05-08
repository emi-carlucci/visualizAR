package com.example.visualizar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


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
        progressBar!!.visibility = View.INVISIBLE
        signInBtn!!.setOnClickListener {
            userNameTxt = findViewById<View>(R.id.user_name_txt) as EditText
            passwordTxt = findViewById<View>(R.id.password_txt) as EditText
            val userName = userNameTxt!!.text.toString()
            val password = passwordTxt!!.text.toString()
            if (isUserFormValid(userName, password)) {
                if (userName == "prueba" && password == "Prueba"){
                    val loginIntent = Intent(this, MainActivity::class.java).apply {
                        putExtra("loginIntentData", "Login OK")
                    }
                    startActivity(loginIntent)
                }
                else {
                    Toast.makeText(this, "Datos inválidos", Toast.LENGTH_LONG).show()
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