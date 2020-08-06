package com.ellen.indicator

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

abstract class Adapter<T : BaseViewHolder> {

    lateinit var allPowerIndicator: AllPowerIndicator

    protected var viewPager:ViewPager? = null
    protected var viewPager2: ViewPager2? = null

    /**
     * tab的类型
     */
    abstract fun getItemType(position: Int): Int

    /**
     * 绑定Tab的ViewHolder
     */
    abstract fun getViewHolder(viewType: Int): T

    /**
     * tab的个数
     */
    abstract fun getItemSize(): Int

    /**
     * tab显示内容
     */
    abstract fun showContent(holder: T)

    /**
     * 初始化Tab
     */
    abstract fun initTab(holder: T)

    /**
     * Tab选择时调用
     */
    abstract fun onTabReselected(holder: T)

    /**
     * Tab未选中的时候调用
     */
    abstract fun onTabUnselected(holder: T)

    /**
     * Tab选择时调用
     */
    abstract fun onTabSelected(holder: T)

    /**
     * 绑定联动的View
     */
    abstract fun bindLinkageView(allPowerIndicator: AllPowerIndicator)

    /**
     * 设置TabLayout的属性
     */
    abstract fun settingTabLayout(tabLayout: TabLayout)
    fun bindViewPager(viewPager: ViewPager){
        this.viewPager = viewPager
    }
    fun bindViewPager2(viewPager2: ViewPager2){
        this.viewPager2 = viewPager2
    }

    /**
     * 初始化显示的位置
     */
    open fun getFirstPosition():Int{
        return 0
    }

    /**
     * 刷新所有的Tab
     */
    open fun notifyDataSetChanged() {
        for (position in 0 until getItemSize()) {
            val baseViewHolder =
               allPowerIndicator.getTabAt(position)?.customView?.tag as T
            showContent(baseViewHolder)
        }
    }
}


