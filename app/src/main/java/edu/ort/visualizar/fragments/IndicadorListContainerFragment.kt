package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import edu.ort.visualizar.R
import edu.ort.visualizar.activities.MainActivity.Companion.ocbUtils


class IndicadorListContainerFragment : Fragment() {

    lateinit var indicatorListView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        indicatorListView = inflater.inflate(R.layout.fragment_indicador_list_container, container, false)
        fillList()
        return indicatorListView
    }

    private fun fillList(){
        var indicators = ocbUtils.getKpiList()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        if (indicators != null && indicators!!.isNotEmpty()) {
            transaction.replace(R.id.fragment_indicador_list_placeholder, IndicadorListFragment(
                indicators!!
            )).commit()
        } else {
            transaction.replace(R.id.fragment_indicador_list_placeholder, IndicadorEmptyListFragment()).commit()
        }
    }

}