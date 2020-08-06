package com.ellen.indicator

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
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

    private fun <T : BaseViewHolder>  handlerAdapter(adapter: Adapter<T>){
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
            tab?.customView?.let { adapter.initTab(baseViewHolder) }
            tab?.customView?.let { adapter.showContent(baseViewHolder) }

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
                adapter.reSelectedStatus(baseViewHolder)
                adapter.onTabSelectedListener?.reSelected(baseViewHolder)
            }

            override fun onTabUnselected(tab: Tab?) {
                val baseViewHolder = tab?.customView?.tag as T
                adapter.unSelectedStatus(baseViewHolder)
                adapter.onTabSelectedListener?.unSelected(baseViewHolder)
            }

            override fun onTabSelected(tab: Tab?) {
                val baseViewHolder = tab?.customView?.tag as T
                adapter.selectedStatus(baseViewHolder)
                adapter.onTabSelectedListener?.selected(baseViewHolder)
            }
        })

        //这里可以进一步优化
        val tab = getTabAt(adapter.getFirstPosition())
        selectTab(tab)
        adapter.selectedStatus(tab?.customView?.tag as T)
    }

    override fun <T : BaseViewHolder> bindViewPager(adapter: Adapter<T>,viewPager: ViewPager) {
        adapter.bindViewPager(viewPager)
        handlerAdapter(adapter)
    }

    override fun <T : BaseViewHolder> bindViewPager2(adapter: Adapter<T>, viewPager2: ViewPager2) {
        adapter.bindViewPager2(viewPager2)
        handlerAdapter(adapter)
    }
}

private interface Indicator {
    fun <T : BaseViewHolder> bindViewPager(adapter: Adapter<T>,viewPager: ViewPager)
    fun <T : BaseViewHolder> bindViewPager2(adapter: Adapter<T>,viewPager2: ViewPager2)
}

