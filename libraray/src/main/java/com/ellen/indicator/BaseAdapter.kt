package com.ellen.indicator

import com.google.android.material.tabs.TabLayout

/**
 * 可用于指示器和Bar
 *
 * 特殊的封装（例如：不绑定ViewPager2 & ViewPager）继承于此类
 *
 */
abstract class BaseAdapter<T : BaseViewHolder> : Adapter<T>(){

    override fun settingTabLayout(tabLayout: TabLayout) {
    }

    final override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        for(position in 0 until getItemSize()){
            val tab = allPowerIndicator.newTab()
            allPowerIndicator.addTab(tab)
        }
    }
}