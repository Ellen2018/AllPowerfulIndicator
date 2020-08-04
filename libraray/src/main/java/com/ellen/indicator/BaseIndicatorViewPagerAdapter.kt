package com.ellen.indicator

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseIndicatorViewPagerAdapter<T : BaseViewHolder> : Adapter<T>{

    protected var viewPager: ViewPager? = null
    protected var viewPager2: ViewPager2? = null

    constructor(viewPager: ViewPager){
        this.viewPager = viewPager
    }

    constructor(viewPager2: ViewPager2){
        this.viewPager2 = viewPager2
    }

    override fun getItemType(position: Int): Int {
      return 0
    }

    override fun getItemSize(): Int {
        return if(viewPager != null){
            viewPager?.adapter?.count!!
        }else {
            viewPager2?.adapter?.itemCount!!
        }
    }

    override fun showContent(holder: T) {

    }

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        if(viewPager != null){
            allPowerIndicator.tabLayout.setupWithViewPager(viewPager)
        }else{
            viewPager2?.let {
                TabLayoutMediator(allPowerIndicator.tabLayout, it) { _, _ ->}.attach()
            }
        }
        allPowerIndicator.tabLayout.tabRippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        allPowerIndicator.tabLayout.setSelectedTabIndicator(null)
    }
}