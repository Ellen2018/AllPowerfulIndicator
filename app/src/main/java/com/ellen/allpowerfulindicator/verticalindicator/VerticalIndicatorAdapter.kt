package com.ellen.allpowerfulindicator.verticalindicator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.Adapter
import com.ellen.indicator.view.BaseIndicatorViewHolder

class VerticalIndicatorAdapter : Adapter<VerticalIndicatorAdapter.IndicatorViewHolder>() {

    var color = Color.RED

    class IndicatorViewHolder(itemView: View) : BaseIndicatorViewHolder(itemView) {
        val view: TextView = itemView.findViewById(R.id.view)

        override fun isResponseStatus(position: Int): Boolean {
            return position != 1
        }
    }

    override fun getStatusItemCount(): Int {
        return 4
    }

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tab_layout_round_view, parent, false)
        return IndicatorViewHolder(view)
    }


    override fun getNoStatusItemCount(): Int {
        return 1
    }

    override fun showContent(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.YELLOW)
        when(position){
            0->holder.view.text = "sdasdasdasdsadasdsadas"
            2->holder.view.text = "dasdsadas"
            else->holder.view.text = "测试"
        }
    }

    override fun selectedStatus(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.BLUE)
    }

    override fun unSelectedStatus(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.GRAY)
    }

    override fun reSelectedStatus(position: Int, holder: IndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.GREEN)
    }

    override fun inStatusPosition(position: Int): Int {
        return when {
            position >= 1 -> {
                position+1
            }
            else -> {
                return position
            }
        }
    }

    override fun outStatusPosition(position: Int): Int {
        return when {
            position >= 1 -> {
                position-1
            }
            else -> {
                return position
            }
        }
    }

    override fun outNoStatusPosition(position: Int): Int {
        return if(position == 1){
            0
        }else{
            super.outNoStatusPosition(position)
        }
    }
}