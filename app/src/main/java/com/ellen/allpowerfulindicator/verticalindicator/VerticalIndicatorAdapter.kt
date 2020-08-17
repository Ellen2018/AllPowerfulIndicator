package com.ellen.allpowerfulindicator.verticalindicator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.vertical.Adapter
import com.ellen.indicator.vertical.BaseIndicatorViewHolder

class VerticalIndicatorAdapter : Adapter<VerticalIndicatorAdapter.IndicatorViewHolder>() {

    var color = Color.RED

    class IndicatorViewHolder(itemView: View) : BaseIndicatorViewHolder(itemView) {
        val view: View = itemView.findViewById(R.id.view)
    }

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tab_layout_round_view, parent, false)
        return IndicatorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100000
    }

    override fun showContent(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.YELLOW)
    }

    override fun selectedStatus(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.BLUE)
    }

    override fun unSelectedStatus(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.GRAY)
    }

    override fun reSelectedStatus(position: Int, holder: IndicatorViewHolder) {

    }
}