package edu.ort.visualizar.holders

import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R
import edu.ort.visualizar.fragments.HomeFragmentDirections
import edu.ort.visualizar.models.KpiModel

class IndicatorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var kpiData: KpiModel
    var itemName: TextView = itemView.findViewById(R.id.kpiName)
    var itemValue: TextView = itemView.findViewById(R.id.kpiValue)
    var itemDate: TextView = itemView.findViewById(R.id.kpiDate)
    var view: View = itemView

    init {
        view.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToAccionesIndicadorFragment(kpiData)
            view.findNavController().navigate(action)
        }
    }

    fun setKpiModel(kpiModel: KpiModel){
        kpiData = kpiModel
    }
}


