package com.ellen.allpowerfulindicator.wy

import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.indicator.horizontal.expand.topbar.BaseTopBarAdapter
import com.google.android.material.tabs.TabLayout

class WyAdapter : BaseTopBarAdapter<WyViewHolderHorizontal>(){
    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): WyViewHolderHorizontal? {
        return WyViewHolderHorizontal(R.layout.view_wy)
    }

    override fun showContent(holder: WyViewHolderHorizontal) {

    }

    override fun initTab(holder: WyViewHolderHorizontal) {
       holder.tv.setTextColor(Color.GRAY)
    }

    override fun selectedStatus(holder: WyViewHolderHorizontal) {
        holder.tv.setTextColor(Color.BLUE)
        val mAnimation =  AnimationUtils.loadAnimation(
            allPowerIndicator.context,
            R.anim.wy_selected_scale
        )
        holder.itemView?.startAnimation(mAnimation)
    }

    override fun unSelectedStatus(holder: WyViewHolderHorizontal) {
        holder.tv.setTextColor(Color.GRAY)
        val mAnimation =  AnimationUtils.loadAnimation(
            allPowerIndicator.context,
            R.anim.wy_unselected_scale
        )
        holder.itemView?.startAnimation(mAnimation)
    }

    override fun reSelectedStatus(holder: WyViewHolderHorizontal) {

    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        super.settingTabLayout(tabLayout)
        tabLayout.tabMode = TabLayout.MODE_FIXED
    }
}



class WyViewHolderHorizontal(layoutId:Int) : BaseHorizontalIndicatorViewHolder(layoutId){

    lateinit var tv:TextView

    override fun bindView(itemView: View) {
       tv = itemView.findViewById(R.id.tv)
    }
}