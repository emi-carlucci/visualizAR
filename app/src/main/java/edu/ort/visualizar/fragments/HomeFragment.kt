package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import edu.ort.visualizar.R

class HomeFragment : Fragment() {

    lateinit var homeView : View
    lateinit var btnAddIndicator : FloatingActionButton
    lateinit var searchInput : TextInputEditText
    lateinit var searchButton : Button
    lateinit var resetButton : Button

    override fun onResume() {
        super.onResume()
        searchButton.visibility = View.VISIBLE
        resetButton.visibility = View.GONE
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.replace(R.id.indicador_list_fragment_container, IndicadorListContainerFragment())
        ft.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeView = inflater.inflate(R.layout.fragment_home, container, false)
        btnAddIndicator = homeView.findViewById(R.id.floatingActionButton)
        searchInput = homeView.findViewById(R.id.searchInput)
        searchButton = homeView.findViewById(R.id.searchButton)
        resetButton = homeView.findViewById(R.id.resetButton)
        btnAddIndicator.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAltaIndicadorFragment()
            homeView.findNavController().navigate(action)
        }
        searchButton.setOnClickListener{
            var kpiIdData = searchInput.text.toString()
            if (kpiIdData != ""){
                searchButton.visibility = View.GONE
                resetButton.visibility = View.VISIBLE
                val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
                ft.replace(R.id.indicador_list_fragment_container, IndicadorSearchListContainerFragment(kpiIdData))
                ft.commit()
            } else {
                Toast.makeText(activity,"Ingresá un valor válido",Toast.LENGTH_SHORT).show()
            }
        }
        resetButton.setOnClickListener{
            searchButton.visibility = View.VISIBLE
            resetButton.visibility = View.GONE
            val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
            ft.replace(R.id.indicador_list_fragment_container, IndicadorListContainerFragment())
            ft.commit()
        }
        return homeView
    }
}