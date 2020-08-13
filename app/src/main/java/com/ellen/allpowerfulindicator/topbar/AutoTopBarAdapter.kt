package com.ellen.allpowerfulindicator.topbar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.indicator.horizontal.expand.topbar.BaseTopBarAdapter

class AutoTopBarAdapter : BaseTopBarAdapter<AutoViewHolderHorizontal>() {
    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): AutoViewHolderHorizontal? {
        return AutoViewHolderHorizontal(R.layout.view_auto_top_bar)
    }

    override fun showContent(holder: AutoViewHolderHorizontal) {
        holder.tvCount.text = "${holder.position}"
    }

    override fun initTab(holder: AutoViewHolderHorizontal) {
        unSelectedStatus(holder)
    }

    override fun selectedStatus(holder: AutoViewHolderHorizontal) {
        holder.tv.setTextColor(Color.BLUE)
        holder.tvCount.setTextColor(Color.BLUE)
        holder.tvCount.visibility = View.VISIBLE
    }

    override fun unSelectedStatus(holder: AutoViewHolderHorizontal) {
        holder.tv.setTextColor(Color.GRAY)
        holder.tvCount.setTextColor(Color.GRAY)
        holder.tvCount.visibility = View.GONE
    }

    override fun reSelectedStatus(holder: AutoViewHolderHorizontal) {

    }
}


class AutoViewHolderHorizontal(layoutId: Int) : BaseHorizontalIndicatorViewHolder(layoutId) {

    lateinit var tv: TextView
    lateinit var tvCount: TextView

    override fun bindView(itemView: View) {
        tv = itemView.findViewById(R.id.tv)
        tvCount = itemView.findViewById(R.id.tv_count)
    }
}