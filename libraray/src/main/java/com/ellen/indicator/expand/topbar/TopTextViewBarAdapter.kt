package com.ellen.indicator.expand.topbar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerfulIndicator
import com.ellen.indicator.view.BaseIndicatorViewHolder
import com.ellen.libraray.R

class TopTextViewBarAdapter : Adapter<TopTextViewBarAdapter.TopTextViewHolder>(){

    class TopTextViewHolder(itemView: View) : BaseIndicatorViewHolder(itemView){
        var tv:TextView = itemView.findViewById(R.id.tv)
    }

    override fun onAttach(allPowerfulIndicator: AllPowerfulIndicator) {
        super.onAttach(allPowerfulIndicator)
        allPowerfulIndicator.trackMode = AllPowerfulIndicator.TrackMode.ALL
    }

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): TopTextViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_bar_text_view,parent,false)
        return TopTextViewHolder(
            view
        )
    }

    override fun showContent(position: Int, holder: TopTextViewHolder) {
         holder.tv.text = "$position"
    }

    override fun selectedStatus(position: Int, holder: TopTextViewHolder) {
        holder.tv.setTextColor(Color.RED)
    }

    override fun unSelectedStatus(position: Int, holder: TopTextViewHolder) {
        holder.tv.setTextColor(Color.BLUE)
    }

    override fun reSelectedStatus(position: Int, holder: TopTextViewHolder) {

    }

}