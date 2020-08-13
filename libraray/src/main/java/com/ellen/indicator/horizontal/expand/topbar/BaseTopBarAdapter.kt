package com.ellen.indicator.horizontal.expand.topbar

import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.google.android.material.tabs.TabLayout

abstract class BaseTopBarAdapter<T : BaseIndicatorViewHolder> : Adapter<T>(){

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_AUTO
    }
}