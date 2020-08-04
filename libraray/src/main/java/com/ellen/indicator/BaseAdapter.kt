package com.ellen.indicator

import com.google.android.material.tabs.TabLayout

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