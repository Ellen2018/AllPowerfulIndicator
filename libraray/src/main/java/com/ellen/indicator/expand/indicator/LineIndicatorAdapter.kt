package com.ellen.indicator.expand.indicator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerfulIndicator
import com.ellen.indicator.view.BaseIndicatorViewHolder
import com.ellen.libraray.R

class LineIndicatorAdapter : Adapter<LineIndicatorAdapter.LineIndicatorViewHolder>(){

    class LineIndicatorViewHolder(itemView: View) : BaseIndicatorViewHolder(itemView){
        val view: View = itemView.findViewById<View>(R.id.view)
    }

    override fun onAttach(allPowerfulIndicator: AllPowerfulIndicator) {
        super.onAttach(allPowerfulIndicator)
        allPowerfulIndicator.mode = AllPowerfulIndicator.Mode.SCROLL
    }

    override fun getItemType(position: Int): Int {
       return 0
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): LineIndicatorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tab_layout_rectangle_view,parent,false)
        return LineIndicatorViewHolder(
            view
        )
    }

    override fun showContent(position: Int, holder: LineIndicatorViewHolder) {

    }

    override fun selectedStatus(position: Int, holder: LineIndicatorViewHolder) {
       holder.view.setBackgroundColor(Color.RED)
    }

    override fun unSelectedStatus(position: Int, holder: LineIndicatorViewHolder) {
        holder.view.setBackgroundColor(Color.GRAY)
    }

    override fun reSelectedStatus(position: Int, holder: LineIndicatorViewHolder) {

    }

}