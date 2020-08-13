package com.ellen.indicator.horizontal.expand.bottombar

import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.google.android.material.tabs.TabLayout

abstract class BaseBottomBar<T : BaseIndicatorViewHolder> : Adapter<T>() {

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setSelectedTabIndicator(null)
    }
}