package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R
import edu.ort.visualizar.adapters.ListIndicatorAdapter
import edu.ort.visualizar.holders.IndicatorHolder
import edu.ort.visualizar.models.KpiModel


class IndicadorListFragment(kpiList: List<KpiModel>) : Fragment() {

    lateinit var v: View
    lateinit var recycleIndicadorItemList : RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<IndicatorHolder>? = null
    var indicators : List<KpiModel> = kpiList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_indicador_list, container, false)
        recycleIndicadorItemList = v.findViewById(R.id.recycleIndicadorItemList)
        return v
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycleIndicadorItemList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recycleIndicadorItemList.layoutManager = layoutManager
        adapter = ListIndicatorAdapter(indicators)
        recycleIndicadorItemList.adapter = adapter
    }

}