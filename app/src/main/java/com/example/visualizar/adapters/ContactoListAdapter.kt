package com.example.visualizar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.visualizar.R
import com.example.visualizar.entities.Contacto
import com.example.visualizar.holders.ContactoHolder

class ContactoListAdapter(
    private var contactsList: MutableList<Contacto>/*,
    val onItemClick: (Int) -> Boolean*/

) : RecyclerView.Adapter<ContactoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_contacto,parent,false)
        return (ContactoHolder(view))
    }

    companion object {

        private val TAG = "ContactoListAdapter"
    }

    override fun getItemCount(): Int {

        return contactsList.size
    }

    fun setData(newData: ArrayList<Contacto>) {
        this.contactsList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactoHolder, position: Int) {

        holder.setName(contactsList[position].nombre)



    }
}