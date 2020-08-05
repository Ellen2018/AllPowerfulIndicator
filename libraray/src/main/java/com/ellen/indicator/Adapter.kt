package com.ellen.indicator

import com.google.android.material.tabs.TabLayout

abstract class Adapter<T : BaseViewHolder> {

    lateinit var allPowerIndicator: AllPowerIndicator

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

    open fun notifyDataSetChanged() {
        for (position in 0 until getItemSize()) {
            val baseViewHolder =
               allPowerIndicator.getTabAt(position)?.customView?.tag as T
            showContent(baseViewHolder)
        }
    }
}


