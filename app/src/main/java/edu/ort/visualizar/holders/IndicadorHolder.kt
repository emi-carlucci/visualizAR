package edu.ort.visualizar.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.ort.visualizar.R

class IndicadorHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        this.view = v
        this.view.tag = this
    }

    fun setListener (mOnItemClickListener : View.OnClickListener){
        this.view.setOnClickListener(mOnItemClickListener)
    }

    fun setName(name: String) {
        val txt: TextView = view.findViewById(R.id.txt_name_item)
        txt.text = name
    }

    fun getCardLayout (): CardView {
        return view.findViewById(R.id.card_package_item)
    }


//
//        fun getImageView () : ImageView {
//            return view.findViewById(R.id.img_item)
//        }
}