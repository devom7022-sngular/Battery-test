package com.example.cpu.bad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class MainAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val dataList = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun add(data: Data) {
        this.dataList.add(data)
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title by lazy { itemView.findViewById<TextView>(R.id.title) }
    private val date by lazy { itemView.findViewById<TextView>(R.id.date) }
    private val image by lazy { itemView.findViewById<ImageView>(R.id.image) }

    fun bind(data: Data) {
        title.text = data.title
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        date.text = simpleDateFormat.format(data.date)
    }
}

data class Data(val title:String, val date : Long, @DrawableRes val imageRes:Int)