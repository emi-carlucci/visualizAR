package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.findNavController
import edu.ort.visualizar.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : Fragment() {

    lateinit var v : View
    lateinit var btnAddIndicator : FloatingActionButton
    lateinit var svIndicador : SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        btnAddIndicator = v.findViewById(R.id.floatingActionButton)
        svIndicador = v.findViewById(R.id.svIndicador)
        return v
    }

   override fun onStart() {
       super.onStart()
       btnAddIndicator.setOnClickListener{
           val action = HomeDirections.actionHome3ToIndicadorALTA()
           v.findNavController().navigate(action)
        }
       svIndicador.setOnClickListener{
           val action = HomeDirections.actionHome3ToFragmentAcciones()
           v.findNavController().navigate(action)
       }


   }
}