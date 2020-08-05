package com.ellen.indicator.expand.indicator

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseIndicatorViewPagerAdapter<T : BaseViewHolder> :
    Adapter<T>() {

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
    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabRippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setSelectedTabIndicator(null)
    }
}