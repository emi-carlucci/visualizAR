package com.example.visualizar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.google.android.material.snackbar.Snackbar


class Indicador_ALTA : Fragment() {

    lateinit var v : View
    lateinit var btnGrabar : Button
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    var validarAction : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.indicador_alta, container, false)
        btnGrabar = v.findViewById(R.id.btnGrabar)
        inputTitulo = v.findViewById(R.id.inputIndicadorTitulo)
        inputDescripcion = v.findViewById(R.id.inputIndicadorDescripcion)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnGrabar.setOnClickListener{
            if(inputTitulo.text.toString().equals("")) {
                    Snackbar.make(v, "Ingrese el título.", Snackbar.LENGTH_SHORT).show()
                    validarAction = false
            }
            if(inputDescripcion.text.toString().equals("")){
                Snackbar.make(v, "Ingrese la descripción.", Snackbar.LENGTH_SHORT).show()
                validarAction = false
            }
            if(validarAction){

                // Aca se persiste en la BD
                println("GRABANDO....")

                Thread.sleep(2000)

                println("vuelvo a HOME")

            }
            validarAction=true
         }
    }


}