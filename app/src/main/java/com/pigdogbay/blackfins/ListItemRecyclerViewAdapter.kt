package com.pigdogbay.blackfins

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pigdogbay.blackfins.model.ListItemData

/**
 * Created by mark on 02/03/18.
 * Generic Recycler view adapter for list_item
 */
class ListItemRecyclerViewAdapter : RecyclerView.Adapter<ListItemViewHolder>(){
    var log : List<ListItemData>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item,parent,false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return log?.size ?: 0
    }

    override fun onBindViewHolder(holder: ListItemViewHolder?, position: Int) {
        if (log!=null) holder?.bind(log!![position])
    }
}

class ListItemViewHolder(val view : View) : RecyclerView.ViewHolder(view){
    private val titleView : TextView = view.findViewById(R.id.textTitle)
    private val subTitleView : TextView = view.findViewById(R.id.textSubtitle)
    private val imageView : ImageView = view.findViewById(R.id.imageIcon)

    fun bind(listItemData: ListItemData){
        titleView.text = listItemData.title
        subTitleView.text = listItemData.subtitle
        val drawable = ContextCompat.getDrawable(imageView.context,listItemData.iconId)
        imageView.setImageDrawable(drawable)
    }
}