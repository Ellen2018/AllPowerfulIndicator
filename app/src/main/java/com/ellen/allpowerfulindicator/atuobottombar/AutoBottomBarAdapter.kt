package com.ellen.allpowerfulindicator.atuobottombar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.indicator.horizontal.expand.bottombar.BaseBottomBar

/**
 * 通过继承
 */
class AutoBottomBarAdapter : BaseBottomBar<AutoBottomBarAdapter.AutoBottomViewHolderHorizontal>() {


    class AutoBottomViewHolderHorizontal(layoutId: Int) : BaseHorizontalIndicatorViewHolder(layoutId) {

        lateinit var tv: TextView

        override fun bindView(itemView: View) {
            tv = itemView.findViewById(R.id.tv)
        }

    }

    override fun getViewHolder(viewType: Int): AutoBottomViewHolderHorizontal {
        return AutoBottomViewHolderHorizontal(R.layout.view_auto_bottom_tab)
    }

    override fun showContent(holder: AutoBottomViewHolderHorizontal) {
        holder.tv.text = "${holder.position}"
    }

    override fun initTab(holder: AutoBottomViewHolderHorizontal) {
         holder.tv.setTextColor(Color.GRAY)
    }

    override fun selectedStatus(holder: AutoBottomViewHolderHorizontal) {
        holder.tv.setTextColor(Color.BLUE)
    }

    override fun unSelectedStatus(holder: AutoBottomViewHolderHorizontal) {
        holder.tv.setTextColor(Color.GRAY)
    }

    override fun reSelectedStatus(holder: AutoBottomViewHolderHorizontal) {
        holder.tv.setTextColor(Color.BLUE)
    }

}