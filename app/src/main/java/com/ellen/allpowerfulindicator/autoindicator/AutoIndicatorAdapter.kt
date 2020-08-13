package com.ellen.allpowerfulindicator.autoindicator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.expand.indicator.BaseIndicatorViewPagerAdapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder

class AutoIndicatorAdapter(var strList:MutableList<String>) :
    BaseIndicatorViewPagerAdapter<AutoIndicatorIndicatorViewHolder>() {

    override fun getViewHolder(viewType: Int): AutoIndicatorIndicatorViewHolder {
        return AutoIndicatorIndicatorViewHolder(R.layout.item_my_auto_indicator)
    }

    override fun showContent(holder: AutoIndicatorIndicatorViewHolder) {
        holder.tv.text = strList[holder.position]
    }

    override fun initTab(holder: AutoIndicatorIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun selectedStatus(holder: AutoIndicatorIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

    override fun unSelectedStatus(holder: AutoIndicatorIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun reSelectedStatus(holder: AutoIndicatorIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

}


class AutoIndicatorIndicatorViewHolder(layoutId: Int) : BaseIndicatorViewHolder(layoutId) {
    lateinit var tv: TextView
    lateinit var iv: ImageView
    override fun bindView(itemView: View) {
        tv = itemView.findViewById(R.id.tv)
        iv = itemView.findViewById(R.id.iv)
    }
}