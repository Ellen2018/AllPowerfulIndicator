package com.ellen.allpowerfulindicator.verticalindicator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.vertical.Adapter
import com.ellen.indicator.vertical.BaseVerticalIndicatorViewHolder

class VerticalIndicatorAdapter : Adapter<VerticalIndicatorAdapter.VerticalIndicatorViewHolder>() {

    class VerticalIndicatorViewHolder(itemView: View) : BaseVerticalIndicatorViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.tv)
        val iv = itemView.findViewById<ImageView>(R.id.iv)
    }

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): VerticalIndicatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_auto_indicator, parent, false)
        return VerticalIndicatorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun showContent(position: Int, holder: VerticalIndicatorViewHolder) {
        holder.tv.text = "$position"
    }

    override fun selectedStatus(position: Int, holder: VerticalIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

    override fun unSelectedStatus(position: Int, holder: VerticalIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun reSelectedStatus(position: Int, holder: VerticalIndicatorViewHolder) {

    }

}