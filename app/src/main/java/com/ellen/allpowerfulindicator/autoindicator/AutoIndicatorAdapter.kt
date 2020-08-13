package com.ellen.allpowerfulindicator.autoindicator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.expand.indicator.BaseIndicatorViewPagerAdapter
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder

class AutoIndicatorAdapter(var strList:MutableList<String>) :
    BaseIndicatorViewPagerAdapter<AutoIndicatorHorizontalIndicatorViewHolder>() {

    override fun getViewHolder(viewType: Int): AutoIndicatorHorizontalIndicatorViewHolder {
        return AutoIndicatorHorizontalIndicatorViewHolder(R.layout.item_my_auto_indicator)
    }

    override fun showContent(holder: AutoIndicatorHorizontalIndicatorViewHolder) {
        holder.tv.text = strList[holder.position]
    }

    override fun initTab(holder: AutoIndicatorHorizontalIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun selectedStatus(holder: AutoIndicatorHorizontalIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

    override fun unSelectedStatus(holder: AutoIndicatorHorizontalIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun reSelectedStatus(holder: AutoIndicatorHorizontalIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

}


class AutoIndicatorHorizontalIndicatorViewHolder(layoutId: Int) : BaseHorizontalIndicatorViewHolder(layoutId) {
    lateinit var tv: TextView
    lateinit var iv: ImageView
    override fun bindView(itemView: View) {
        tv = itemView.findViewById(R.id.tv)
        iv = itemView.findViewById(R.id.iv)
    }
}