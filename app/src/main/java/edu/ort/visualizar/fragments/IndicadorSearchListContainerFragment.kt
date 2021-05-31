package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import edu.ort.visualizar.R
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils
import edu.ort.visualizar.models.KpiModel


class IndicadorSearchListContainerFragment(kpiId : String) : Fragment() {

    lateinit var indicatorListView: View
    var kpiIdValue : String = kpiId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        indicatorListView = inflater.inflate(R.layout.fragment_indicador_list_container, container, false)
        fillList()
        return indicatorListView
    }

    private fun fillList(){
        var indicator = ocbUtils.getKpi(kpiIdValue)
        var indicators : List<KpiModel>? = null
        if (indicator != null){
            indicators = listOf(indicator)
        }
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        if (indicators != null && indicators.isNotEmpty()) {
            transaction.replace(R.id.fragment_indicador_list_placeholder, IndicadorListFragment(
                indicators
            )).commit()
        } else {
            transaction.replace(R.id.fragment_indicador_list_placeholder, IndicadorEmptyListFragment()).commit()
        }
    }
}