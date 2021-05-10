package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R

import edu.ort.visualizar.entities.Indicador
import edu.ort.visualizar.adapters.ListIndicadorAdapter

class IndicadorListFragment : Fragment() {

    lateinit var v: View
    lateinit var recycleIndicadorItemList : RecyclerView

    var indicadors : MutableList<Indicador> = ArrayList<Indicador>()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listIndicadorAdapter: ListIndicadorAdapter

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
            indicadors.add(Indicador("Indicador Nº $i",26, Indicador.Constants.cursoA))
        }

        //Configuración Obligatoria
        recycleIndicadorItemList.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recycleIndicadorItemList.layoutManager = linearLayoutManager

        //TODO: Setear el onclick para cada item del indicador.
        /**
        listIndicadorAdapter = ListIndicadorAdapter(indicadors) { x ->
            onItemClick(x)
        }
         */

        recycleIndicadorItemList.adapter = listIndicadorAdapter

    }

    fun onItemClick ( position : Int ) : Boolean {
        //TODO: Enviar informacion al siguiente fragment del indicador seleccionado.
        val action = IndicadorListFragmentDirections.actionIndicadorListFragmentToAccionesIndicadorFragment()
        v.findNavController().navigate(action)
        return true
    }
}