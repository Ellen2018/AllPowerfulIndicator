package com.ellen.indicator

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout

/**
 * 3种使用模式
 * 1.绑定ViewPager
 * 2.绑定ViewPager2
 * 3.自由模式
 */
class AllPowerIndicator : TabLayout, Indicator {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )


    override fun <T : BaseViewHolder> setAdapter(adapter: Adapter<T>) {
        adapter.allPowerIndicator = this
        adapter.bindLinkageView(this)
        adapter.settingTabLayout(this)
        val itemCount = adapter.getItemSize()
        var isReset = false
        for (position in 0 until itemCount) {
            val viewType = adapter.getItemType(position)
            val tab = getTabAt(position)
            val baseViewHolder = adapter.getViewHolder(viewType)
            tab?.setCustomView(baseViewHolder.layoutId)
            baseViewHolder.itemView = tab?.customView
            baseViewHolder.position = position
            baseViewHolder.viewType = viewType
            tab?.customView?.tag = baseViewHolder
            tab?.customView?.let { baseViewHolder.bindView(it) }
            tab?.customView?.let { adapter.onTabUnselected(baseViewHolder) }
            tab?.customView?.let { adapter.showContent(baseViewHolder) }
            if (position == 0) {
                tab?.customView?.let { adapter.onTabSelected(baseViewHolder) }
            }

            //重新调整TabLayout的大小
            if (!isReset) {
                val layoutParams = layoutParams
                layoutParams.height = tab?.customView?.layoutParams?.height!!
                this.layoutParams = layoutParams
                isReset = true
            }
        }
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: Tab?) {
                val baseViewHolder = tab?.customView?.tag as T
                adapter.onTabReselected(baseViewHolder)
            }

            override fun onTabUnselected(tab: Tab?) {
                val baseViewHolder = tab?.customView?.tag as T
                adapter.onTabUnselected(baseViewHolder)
            }

            override fun onTabSelected(tab: Tab?) {
                val baseViewHolder = tab?.customView?.tag as T
                adapter.onTabSelected(baseViewHolder)
            }
        })
    }
}

private interface Indicator {
    fun <T : BaseViewHolder> setAdapter(adapter: Adapter<T>)
}

