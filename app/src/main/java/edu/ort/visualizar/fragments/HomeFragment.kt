package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ort.visualizar.R

class HomeFragment : Fragment() {

    lateinit var v : View
    lateinit var btnAddIndicator : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Begin the transaction
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.indicador_list_fragment_container, IndicadorListContainerFragment())
        ft.commit()

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        btnAddIndicator = v.findViewById(R.id.floatingActionButton)
        return v
    }

   override fun onStart() {
       super.onStart()
       btnAddIndicator.setOnClickListener{
           val action = HomeFragmentDirections.actionHomeFragmentToAltaIndicadorFragment()
           v.findNavController().navigate(action)
        }
   }
}