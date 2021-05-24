package edu.ort.visualizar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.R
import edu.ort.visualizar.adapters.ListIndicadorAdapter
import edu.ort.visualizar.entities.Indicador


class IndicadorListFragment : Fragment() {

    lateinit var v: View
    lateinit var recycleIndicadorItemList : RecyclerView

    var indicadors : MutableList<Indicador> = ArrayList<Indicador>()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listIndicadorAdapter: ListIndicadorAdapter

    private val onItemClickListener = View.OnClickListener { view -> //TODO: Step 4 of 4: Finally call getTag() on the view.
        // This viewHolder will have all required values.
        val viewHolder = view.tag as RecyclerView.ViewHolder
        val position = viewHolder.adapterPosition
        val thisItem: Indicador = indicadors[position]
        val action = HomeFragmentDirections.actionHomeFragmentToAccionesIndicadorFragment(position)

        v.findNavController().navigate(action)
    }


    companion object {
        fun newInstance() = IndicadorListFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_indicador_list, container, false)
        recycleIndicadorItemList = v.findViewById(R.id.recycleIndicadorItemList)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        //TODO: Remover la lista de indicadores. La debe recibir por parametro del container.
        for (i in 1..10) {
            indicadors.add(Indicador("Indicador Nº $i", 26, Indicador.Constants.cursoA))
        }

        //Configuración Obligatoria
        recycleIndicadorItemList.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycleIndicadorItemList.layoutManager = linearLayoutManager

        //TODO: Setear el onclick para cada item del indicador.

        listIndicadorAdapter = ListIndicadorAdapter(indicadors, onItemClickListener) /*{ x ->
            onItemClick(x)
        } */

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