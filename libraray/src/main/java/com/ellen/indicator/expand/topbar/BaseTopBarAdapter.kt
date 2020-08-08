package com.ellen.indicator.expand.topbar

import com.ellen.indicator.Adapter
import com.ellen.indicator.BaseIndicatorViewHolder
import com.google.android.material.tabs.TabLayout

abstract class BaseTopBarAdapter<T : BaseIndicatorViewHolder> : Adapter<T>(){

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_AUTO
    }
}