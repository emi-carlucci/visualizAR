package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R
import edu.ort.visualizar.adapters.ListIndicadorAdapter
import edu.ort.visualizar.models.KpiModel


class IndicadorListFragment(kpiList: List<KpiModel>) : Fragment() {

    lateinit var v: View
    lateinit var recycleIndicadorItemList : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listIndicadorAdapter: ListIndicadorAdapter
    var indicators : List<KpiModel> = kpiList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.fragment_indicador_list, container, false)
        recycleIndicadorItemList = v.findViewById(R.id.recycleIndicadorItemList)
        return v
    }

    override fun onResume() {
        super.onResume()

        val onItemClickListener = View.OnClickListener { view ->
            val viewHolder = view.tag as RecyclerView.ViewHolder
            val position = viewHolder.adapterPosition
            val action = HomeFragmentDirections.actionHomeFragmentToAccionesIndicadorFragment(position)
            v.findNavController().navigate(action)
        }

        recycleIndicadorItemList.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycleIndicadorItemList.layoutManager = linearLayoutManager

        //TODO: Setear el onclick para cada item del indicador.

        listIndicadorAdapter = ListIndicadorAdapter(indicators, onItemClickListener)
        listIndicadorAdapter.setOnItemClickListener(onItemClickListener)
        recycleIndicadorItemList.adapter = listIndicadorAdapter

    }

/*
    fun onItemClick(position: Int) : Boolean {
        //TODO: Enviar informacion al siguiente fragment del indicador seleccionado.
        val action = IndicadorListFragmentDirections.actionIndicadorListFragmentToAccionesIndicadorFragment()
        v.findNavController().navigate(action)
        return true
    }*/
}
