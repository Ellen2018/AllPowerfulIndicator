package com.ellen.indicator.horizontal.expand.topbar

import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.google.android.material.tabs.TabLayout

/**
 * 原始顶部导航栏
 */
class OriginalTopBarAdapter : Adapter<BaseIndicatorViewHolder>() {

    private var traverse: Traverse? = null

    override fun getItemType(position: Int): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): BaseIndicatorViewHolder? {
        return null
    }

    override fun showContent(holder: BaseIndicatorViewHolder) {

    }

    override fun initTab(holder: BaseIndicatorViewHolder) {

    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_AUTO
    }

    override fun selectedStatus(holder: BaseIndicatorViewHolder) {

    }

    override fun unSelectedStatus(holder: BaseIndicatorViewHolder) {

    }

    override fun reSelectedStatus(holder: BaseIndicatorViewHolder) {

    }

    override fun bindLinkageView() {
        super.bindLinkageView()
        traverse?.let { traverse(it) }
    }

    override fun isOriginal(): Boolean {
        return true
    }

    fun traverse(traverse: Traverse) {
        if(isBindAllPowerIndicator){
            for (position in 0 until allPowerIndicator.tabCount) {
                val tab = allPowerIndicator.getTabAt(position)
                tab?.let { traverse?.showContent(it) }
            }
        }else{
            this.traverse = traverse
        }
    }

    interface Traverse {
        fun showContent(tab: TabLayout.Tab)
    }
}