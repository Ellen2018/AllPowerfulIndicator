package com.ellen.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun <T:View> findViewById(id:Int):T{
        return itemView.findViewById(id)
    }
}