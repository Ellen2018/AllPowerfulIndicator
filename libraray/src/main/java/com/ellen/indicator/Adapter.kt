package com.ellen.indicator

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

abstract class Adapter<T : BaseViewHolder> {

    lateinit var allPowerIndicator: AllPowerIndicator

    protected var viewPager:ViewPager? = null
    protected var viewPager2: ViewPager2? = null

    abstract fun getItemType(position: Int): Int
    abstract fun getViewHolder(viewType: Int): T
    abstract fun getItemSize(): Int
    abstract fun showContent(holder: T)
    abstract fun initTab(holder: T)
    abstract fun onTabReselected(holder: T)
    abstract fun onTabUnselected(holder: T)
    abstract fun onTabSelected(holder: T)
    abstract fun bindLinkageView(allPowerIndicator: AllPowerIndicator)
    abstract fun settingTabLayout(tabLayout: TabLayout)
    fun bindViewPager(viewPager: ViewPager){
        this.viewPager = viewPager
    }
    fun bindViewPager2(viewPager2: ViewPager2){
        this.viewPager2 = viewPager2
    }

    /**
     * 初始化显示的位置
     */
    open fun getFirstPosition():Int{
        return 0
    }

    open fun notifyDataSetChanged() {
        for (position in 0 until getItemSize()) {
            val baseViewHolder =
               allPowerIndicator.getTabAt(position)?.customView?.tag as T
            showContent(baseViewHolder)
        }
    }
}


