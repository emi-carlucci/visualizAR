package com.example.visualizar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

class Home : Fragment() {

    lateinit var v : View
    lateinit var btnALTA : Button
    lateinit var btnEditar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        btnALTA = v.findViewById(R.id.btnALTA)
        btnEditar = v.findViewById(R.id.btnEditar)
        return v
    }

    override fun onStart() {
        super.onStart()
        btnALTA.setOnClickListener{
            // lleva a FORMULARIO DE ALTA
            println("ALTA de INDICADOR....")

            val action = HomeDirections.actionHome2ToIndicadorALTA()
        btnEditar.setOnClickListener{
            // lleva a FORMULARIO DE EDICIOn
            println("EDITAR INDICADOR....")

            val action = HomeDirections.actionHome2ToIndicadorEditar()
            v.findNavController().navigate(action)
        }
    }
}