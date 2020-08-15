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

        override fun isResponseStatus(position: Int): Boolean {
            return position == 0 || position == 2
        }

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
        return 4
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

    override fun inStatusPosition(position: Int): Int {
        if(position == 0)return 0
        if(position == 1)return 2
        return position
    }

    override fun outStatusPosition(position: Int): Int {
       if(position == 0)return 0
        if(position == 2)return 1
        return position
    }

}