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
import edu.ort.visualizar.activities.MainActivity
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.models.KpiModel

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
        searchInput.text?.clear()
        fillList("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeView = inflater.inflate(R.layout.fragment_home, container, false)
        btnAddIndicator = homeView.findViewById(R.id.floatingActionButton)
        searchInput = homeView.findViewById(R.id.searchInput)
        searchButton = homeView.findViewById(R.id.searchButton)
        resetButton = homeView.findViewById(R.id.resetButton)
        searchInput.text?.clear()
        btnAddIndicator.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAltaIndicadorFragment()
            homeView.findNavController().navigate(action)
        }

        searchButton.setOnClickListener{
            var kpiIdData = searchInput.text.toString()
            if (kpiIdData != ""){
                searchButton.visibility = View.GONE
                resetButton.visibility = View.VISIBLE
                fillList(kpiIdData)
            } else {
                Toast.makeText(activity,"Por favor, ingresá un ID",Toast.LENGTH_SHORT).show()
            }
        }
        resetButton.setOnClickListener{
            searchInput.text?.clear()
            searchButton.visibility = View.VISIBLE
            resetButton.visibility = View.GONE
            fillList("")
        }
        return homeView
    }

    private fun fillList(kpiIdValue: String){
        var indicators : List<KpiModel>? = null
        if(kpiIdValue != ""){
            var indicator = ocbUtils.getKpi(kpiIdValue)
            if (indicator != null){
                indicators = listOf(indicator)
            }
        }else{
            indicators = ocbUtils.getKpiList()!!
        }
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        if (indicators != null && indicators!!.isNotEmpty()) {
            transaction.replace(R.id.indicador_list_fragment_container, IndicadorListFragment(
                indicators!!
            )).commit()
        } else {
            transaction.replace(R.id.indicador_list_fragment_container, IndicadorEmptyListFragment()).commit()
        }
    }
}