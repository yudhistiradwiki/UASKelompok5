package com.yudhistira.uaskelompok5

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yudhistira.uaskelompok5.DBModelBook

class DBAdapterbook(private val listData:ArrayList<DBModelBook>):RecyclerView.Adapter<DBAdapterbook.CardViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    interface OnItemClickCallback {
        fun onItemClicked(data: DBModelBook)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class CardViewHolder(itemV:View):RecyclerView.ViewHolder(itemV) {
        var Nama: TextView = itemV.findViewById(R.id.tvNama)
        var Penulis: TextView = itemV.findViewById(R.id.tvPenulis)
        var Penerbit: TextView = itemV.findViewById(R.id.tvPenerbit)
        var Tahun: TextView = itemV.findViewById(R.id.tvTahun)
        var Sinopsis: TextView = itemV.findViewById(R.id.tvSinopsis)
        var Photo: TextView = itemV.findViewById(R.id.tvPhoto)
        var cvData: CardView = itemV.findViewById(R.id.cvData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBAdapterbook.CardViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_databook, parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DBAdapterbook.CardViewHolder, position: Int) {
        val data = listData[position]
        holder.Nama.text = data.Nama
        holder.Penulis.text = data.Penulis
        holder.Penerbit.text = data.Penerbit
        holder.Tahun.text = data.Tahun
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


}