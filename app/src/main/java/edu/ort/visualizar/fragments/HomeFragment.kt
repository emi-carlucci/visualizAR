package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.ort.visualizar.R

class HomeFragment : Fragment() {

    lateinit var homeView : View
    lateinit var btnAddIndicator : FloatingActionButton

    override fun onResume() {
        super.onResume()
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.indicador_list_fragment_container, IndicadorListContainerFragment())
        ft.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeView = inflater.inflate(R.layout.fragment_home, container, false)
        btnAddIndicator = homeView.findViewById(R.id.floatingActionButton)
        btnAddIndicator.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAltaIndicadorFragment()
            homeView.findNavController().navigate(action)
        }
        return homeView
    }
}