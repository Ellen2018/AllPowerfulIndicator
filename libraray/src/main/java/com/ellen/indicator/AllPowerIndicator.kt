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

    private fun <T : BaseIndicatorViewHolder>  handlerAdapter(adapter: Adapter<T>){
        adapter.allPowerIndicator = this
        adapter.settingTabLayout(this)
        adapter.bindLinkageView()
        if(!adapter.isOriginal()) {
            val itemCount = adapter.getItemSize()
            var isReset = false
            for (position in 0 until itemCount) {
                val viewType = adapter.getItemType(position)
                val tab = getTabAt(position)
                val baseViewHolder: T? = adapter.getViewHolder(viewType)
                baseViewHolder?.layoutId?.let { tab?.setCustomView(it) }
                baseViewHolder?.itemView = tab?.customView
                baseViewHolder?.position = position
                baseViewHolder?.viewType = viewType
                tab?.customView?.tag = baseViewHolder
                tab?.customView?.let { baseViewHolder?.bindView(it) }
                tab?.customView?.let { baseViewHolder?.let { it1 -> adapter.initTab(it1) } }
                tab?.customView?.let { baseViewHolder?.let { it1 -> adapter.showContent(it1) } }

                //重新调整TabLayout的大小
                if (!isReset) {
                    val layoutParams = layoutParams
                    layoutParams.height = tab?.customView?.layoutParams?.height!!
                    this.layoutParams = layoutParams
                    isReset = true
                }
            }
            adapter.initComplete()
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
        }
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager(adapter: Adapter<T>, viewPager: ViewPager) {
        adapter.bindViewPager(viewPager)
        adapter.mode = Adapter.Mode.VIEW_PAGER
        handlerAdapter(adapter)
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager2(adapter: Adapter<T>, viewPager2: ViewPager2) {
        adapter.bindViewPager2(viewPager2)
        adapter.mode = Adapter.Mode.VIEW_PAGER2
        handlerAdapter(adapter)
    }

    override fun <T : BaseIndicatorViewHolder> bindFree(adapter: Adapter<T>) {
        adapter.mode = Adapter.Mode.FREE
        handlerAdapter(adapter)
    }
}

private interface Indicator {
    fun <T : BaseIndicatorViewHolder> bindViewPager(adapter: Adapter<T>, viewPager: ViewPager)
    fun <T : BaseIndicatorViewHolder> bindViewPager2(adapter: Adapter<T>, viewPager2: ViewPager2)
    fun <T : BaseIndicatorViewHolder> bindFree(adapter: Adapter<T>)
}

