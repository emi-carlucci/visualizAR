package edu.ort.visualizar.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.ort.visualizar.R
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.models.UserModel
import edu.ort.visualizar.utils.DBUtils


class LoginActivity : AppCompatActivity() {

    private var dbUtils: DBUtils? = null
    private var signInBtn: Button? = null
    private var userNameTxt: EditText? = null
    private var passwordTxt: EditText? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbUtils = DBUtils()
        signInBtn = findViewById<View>(R.id.signin_btn) as Button
        progressBar = findViewById<View>(R.id.progress_bar_main) as ProgressBar

        progressBar!!.visibility = View.INVISIBLE
        signInBtn!!.visibility = View.VISIBLE

        signInBtn!!.setOnClickListener {
            userNameTxt = findViewById<View>(R.id.user_name_txt) as EditText
            passwordTxt = findViewById<View>(R.id.password_txt) as EditText
            val userName = userNameTxt!!.text.toString()
            val password = passwordTxt!!.text.toString()
            if (isUserFormValid(userName, password)) {
                val signInAsyncTask = LoginProcess()
                val params = arrayOf(userName, password)
                signInAsyncTask.execute(*params)
            }
        }
    }

    inner class LoginProcess: AsyncTask<String?, Void?, UserModel?>() {

        private var userName: String? = null
        private var password: String? = null

        override fun onPreExecute() {
            userNameTxt?.isEnabled = false
            passwordTxt?.isEnabled = false
            progressBar?.visibility = View.VISIBLE
            signInBtn?.visibility = View.GONE

        }

        override fun doInBackground(vararg params: String?): UserModel? {
            userName = params[0]
            password = params[1]
            return dbUtils?.getUser(userName)
        }

        override fun onPostExecute(user: UserModel?) {
            progressBar?.visibility = View.INVISIBLE
            signInBtn?.visibility = View.VISIBLE
            userNameTxt?.isEnabled = true
            passwordTxt?.isEnabled = true
            if (user == null) {
                Toast.makeText(this@LoginActivity, "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show()
            } else {
                var data: ByteArray = Base64.decode(user.password, Base64.DEFAULT)
                var decodedPassword =  String(data, charset("UTF-8"))
                if (password == decodedPassword) {
                    val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show()
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