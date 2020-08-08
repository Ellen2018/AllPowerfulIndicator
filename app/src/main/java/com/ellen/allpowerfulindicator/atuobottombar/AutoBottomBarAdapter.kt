package com.ellen.allpowerfulindicator.atuobottombar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseIndicatorViewHolder
import com.ellen.indicator.expand.bottombar.BaseBottomBar

/**
 * 通过继承
 */
class AutoBottomBarAdapter : BaseBottomBar<AutoBottomBarAdapter.AutoBottomViewHolder>() {


    class AutoBottomViewHolder(layoutId: Int) : BaseIndicatorViewHolder(layoutId) {

        lateinit var tv: TextView

        override fun bindView(itemView: View) {
            tv = itemView.findViewById(R.id.tv)
        }

    }

    override fun getViewHolder(viewType: Int): AutoBottomViewHolder {
        return AutoBottomViewHolder(R.layout.view_auto_bottom_tab)
    }

    override fun showContent(holder: AutoBottomViewHolder) {
        holder.tv.text = "${holder.position}"
    }

    override fun initTab(holder: AutoBottomViewHolder) {
         holder.tv.setTextColor(Color.GRAY)
    }

    override fun selectedStatus(holder: AutoBottomViewHolder) {
        holder.tv.setTextColor(Color.BLUE)
    }

    override fun unSelectedStatus(holder: AutoBottomViewHolder) {
        holder.tv.setTextColor(Color.GRAY)
    }

    override fun reSelectedStatus(holder: AutoBottomViewHolder) {
        holder.tv.setTextColor(Color.BLUE)
    }

}