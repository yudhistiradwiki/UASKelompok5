package com.yudhistira.uaskelompok5

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhistira.uaskelompok5.DBModelBook

class DBAdapterbooks(private val listData:ArrayList<DBModelBook>):RecyclerView.Adapter<DBAdapterbooks.CardViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    interface OnItemClickCallback {
        fun onItemClicked(data: DBModelBook)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class CardViewHolder(itemV:View):RecyclerView.ViewHolder(itemV) {
        var Nama: TextView = itemV.findViewById(R.id.tv_item_name)
        var Penulis: TextView = itemV.findViewById(R.id.tv_item_penulis)
        var Penerbit: TextView = itemV.findViewById(R.id.tv_item_penerbit)
        var Tahun: TextView = itemV.findViewById(R.id.tv_item_tahun)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBAdapterbooks.CardViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DBAdapterbooks.CardViewHolder, position: Int) {
        val data = listData[position]
        Glide.with(holder.itemView.context)
            .load(R.drawable.uu)
            .into(holder.imgPhoto)
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