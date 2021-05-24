package edu.ort.visualizar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R
import edu.ort.visualizar.holders.IndicatorHolder
import edu.ort.visualizar.models.KpiModel
import edu.ort.visualizar.utils.DateUtils


class ListIndicatorAdapter(kpiData: List<KpiModel>)
    : RecyclerView.Adapter<IndicatorHolder>() {

    private var kpiList = kpiData

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): IndicatorHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_kpi, viewGroup, false)
        return IndicatorHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: IndicatorHolder, i: Int) {
        var parsedDate = DateUtils().parseDate(kpiList[i].dateModified?.value.toString())
        viewHolder.setKpiModel(kpiList[i])
        viewHolder.itemName.text = kpiList[i].name?.value.toString()
        viewHolder.itemValue.text = kpiList[i].kpiValue?.value.toString()
        viewHolder.itemDate.text = "Última Modificación: $parsedDate"
    }

    override fun getItemCount(): Int {
        return kpiList.size
    }
}