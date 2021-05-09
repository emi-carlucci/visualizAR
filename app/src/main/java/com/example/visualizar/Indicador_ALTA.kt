package com.example.visualizar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.findNavController

import com.google.android.material.snackbar.Snackbar

class Indicador_ALTA : Fragment() {

    lateinit var v : View
    lateinit var btnConfirm : Button
    lateinit var btnLimpiar : Button
    lateinit var inputTitulo : TextView
    lateinit var inputDescripcion : TextView
    lateinit var inputFormula : TextView
    lateinit var inputFrec : TextView
    lateinit var spinner : Spinner
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
        btnConfirm = v.findViewById(R.id.btnConfirmar)
        btnLimpiar = v.findViewById(R.id.btnLimpiar)
        inputTitulo = v.findViewById(R.id.inputIndicadorTitulo)
        inputDescripcion = v.findViewById(R.id.inputIndicadorDescripcion)
        inputFormula = v.findViewById(R.id.inputFormula)
        inputFrec = v.findViewById(R.id.inputFrecuencia)
        return v
    }

    override fun onStart() {
        super.onStart()


/*
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
*/
        btnConfirm.setOnClickListener{
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
                Thread.sleep(1000)
                println("vuelvo a HOME")
                // val action = Indicador_ALTADirections.actionIndicadorALTAToHome2()
                // v.findNavController().navigate(action)
            }
            validarAction=true
        }
        btnLimpiar.setOnClickListener {
            inputTitulo.setText("")
            inputDescripcion.setText("")
            inputFormula.setText("")
            inputFrec.setText("")
        }
    }
}