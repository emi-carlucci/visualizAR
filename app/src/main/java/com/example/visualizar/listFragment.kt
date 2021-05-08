package com.example.visualizar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.example.visualizar.entities.Contacto
import com.example.visualizar.adapters.ContactoListAdapter

class listFragment : Fragment() {

    lateinit var v: View

    lateinit var recContactos : RecyclerView

    var contactos : MutableList<Contacto> = ArrayList<Contacto>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var contactoListAdapter: ContactoListAdapter

    companion object {
        fun newInstance() = listFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.list_fragment, container, false)
        recContactos = v.findViewById(R.id.rec_contactos)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        for (i in 1..10) {
            contactos.add(Contacto("Indicador Nº $i",26, Contacto.Constants.cursoA))
//            contactos.add(Contacto("Indicador Nº $i",30, Contacto.Constants.cursoA))
     //       contactos.add(Contacto("Indicador Nº $i",28, Contacto.Constants.cursoB))
  //          contactos.add(Contacto("Indicador Nº $i",37, Contacto.Constants.cursoB))
   //         contactos.add(Contacto("Indicador Nº $i", 42, Contacto.Constants.cursoC))
    //        contactos.add(Contacto("Indicador Nº $i",21, Contacto.Constants.cursoC))
        }

        //Configuración Obligatoria
        recContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recContactos.layoutManager = linearLayoutManager


        contactoListAdapter = ContactoListAdapter(contactos);

        /*
        contactoListAdapter = ContactoListAdapter(contactos) { x ->
            onItemClick(x)
        }*/

        recContactos.adapter = contactoListAdapter

    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }



}