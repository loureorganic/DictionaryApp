package com.example.dictionaryapp.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.WordModelItem
import com.google.android.material.button.MaterialButton


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var dataList : ArrayList<WordModelItem> =  arrayListOf()

    var onItemClick: ((WordModelItem) -> Unit)? = null

    fun setDataList(userImageList: ArrayList<WordModelItem>) {
        this.dataList = userImageList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.title.text = data.word
        holder.description.text = data.meanings?.get(0)?.definitions?.get(0)?.definition


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data)
        }
        holder.buttonSeeMore.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.cardItemTitle)
        var description: TextView = itemView.findViewById(R.id.cardItemDescription)
        var buttonSeeMore: MaterialButton = itemView.findViewById(R.id.btnSeeMore)

    }

}