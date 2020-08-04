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
       return allPowerIndicator.tabCount
    }

    override fun showContent(holder: T) {

    }

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        if(viewPager != null){
            allPowerIndicator.setupWithViewPager(viewPager)
        }else{
            viewPager2?.let {
                TabLayoutMediator(allPowerIndicator, it) { _, _ ->}.attach()
            }
        }
        allPowerIndicator.tabRippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        allPowerIndicator.setSelectedTabIndicator(null)
    }
}