package com.ellen.indicator.horizontal.expand.topbar

import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.google.android.material.tabs.TabLayout

abstract class BaseTopBarAdapter<T : BaseHorizontalIndicatorViewHolder> : Adapter<T>(){

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_AUTO
    }
}