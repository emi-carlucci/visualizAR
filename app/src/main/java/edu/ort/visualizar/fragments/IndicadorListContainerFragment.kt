package edu.ort.visualizar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.ort.visualizar.R
import edu.ort.visualizar.adapters.ListIndicadorAdapter
import edu.ort.visualizar.entities.Indicador


class IndicadorListContainerFragment : Fragment() {


    lateinit var indicadorListFragment: Fragment
    lateinit var emptyListFragment: IndicadorEmptyListFragment
    lateinit var v: View

    var indicadors : MutableList<Indicador> = ArrayList<Indicador>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var listIndicadorAdapter: ListIndicadorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: Api Call para obtener los indicadores.

        for (i in 1..10) {
        indicadors.add(Indicador("Indicador Nº $i",26, Indicador.Constants.cursoA))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val indicadorListFragment: Fragment = IndicadorListFragment()
        val emptyListFragment: Fragment = IndicadorEmptyListFragment()
        v = inflater.inflate(R.layout.fragment_indicador_list_container, container, false)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        if (indicadors.size == 0) {
            transaction.replace(R.id.fragment_indicador_list_placeholder, emptyListFragment).commit()
        } else {
            transaction.replace(R.id.fragment_indicador_list_placeholder, indicadorListFragment).commit()
        }
        return v
    }

    companion object {
        fun newInstance() = IndicadorListFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}