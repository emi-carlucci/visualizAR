package edu.ort.visualizar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R
import edu.ort.visualizar.entities.Indicador
import edu.ort.visualizar.holders.IndicadorHolder
import edu.ort.visualizar.models.KpiModel

class ListIndicadorAdapter(private var contactsList: List<KpiModel>,
                           private  var mOnItemClickListener: View.OnClickListener)
    : RecyclerView.Adapter<IndicadorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicadorHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_indicador_item_list, parent, false)
        return (IndicadorHolder(view))
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    //TODO: Step 2 of 4: Assign itemClickListener to your local View.OnClickListener variable
    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        mOnItemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: IndicadorHolder, position: Int) {
        holder.setName(contactsList[position].name?.value.toString())
        holder.setListener(mOnItemClickListener)
    }
}