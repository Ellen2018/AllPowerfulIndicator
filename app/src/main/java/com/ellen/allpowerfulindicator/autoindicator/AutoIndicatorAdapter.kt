package com.ellen.allpowerfulindicator.autoindicator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.expand.indicator.BaseIndicatorViewPagerAdapter
import com.ellen.indicator.BaseViewHolder

class AutoIndicatorAdapter(var strList:MutableList<String>) :
    BaseIndicatorViewPagerAdapter<AutoIndicatorViewHolder>() {

    override fun getViewHolder(viewType: Int): AutoIndicatorViewHolder {
        return AutoIndicatorViewHolder(R.layout.item_my_auto_indicator)
    }

    override fun onTabReselected(holder: AutoIndicatorViewHolder) {

    }

    override fun showContent(holder: AutoIndicatorViewHolder) {
        holder.tv.text = strList[holder.position]
    }

    override fun onTabUnselected(holder: AutoIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

    override fun onTabSelected(holder: AutoIndicatorViewHolder) {
        holder.iv.visibility = View.VISIBLE
        holder.tv.visibility = View.GONE
    }

    override fun initTab(holder: AutoIndicatorViewHolder) {
        holder.iv.visibility = View.GONE
        holder.tv.visibility = View.VISIBLE
    }

}


class AutoIndicatorViewHolder(layoutId: Int) : BaseViewHolder(layoutId) {
    lateinit var tv: TextView
    lateinit var iv: ImageView
    override fun bindView(itemView: View) {
        tv = itemView.findViewById(R.id.tv)
        iv = itemView.findViewById(R.id.iv)
    }
}