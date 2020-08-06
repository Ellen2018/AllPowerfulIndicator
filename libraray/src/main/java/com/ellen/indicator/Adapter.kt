package com.ellen.indicator

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

abstract class Adapter<T : BaseViewHolder> {

    lateinit var allPowerIndicator: AllPowerIndicator
    internal var onTabSelectedListener:OnTabSelectedListener<T>? = null

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
     * 绑定联动的View
     */
    abstract fun bindLinkageView(allPowerIndicator: AllPowerIndicator)

    /**
     * 设置TabLayout的属性
     */
    abstract fun settingTabLayout(tabLayout: TabLayout)

    /**
     * 选中状态
     */
    abstract fun selectedStatus(holder: T)

    /**
     * 未选中状态
     */
    abstract fun unSelectedStatus(holder: T)

    /**
     * 重新选中的状态
     */
    abstract fun reSelectedStatus(holder: T)

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
        //刷新内容
        for (position in 0 until getItemSize()) {
            val baseViewHolder =
               allPowerIndicator.getTabAt(position)?.customView?.tag as T
            showContent(baseViewHolder)
            if(allPowerIndicator.selectedTabPosition == position){
                //更新选中状态
                selectedStatus(baseViewHolder)
            }else{
                //更新未选中状态
                unSelectedStatus(baseViewHolder)
            }
        }
    }

    interface OnTabSelectedListener<T : BaseViewHolder>{
        fun selected(holder: T)
        fun unSelected(holder: T)
        fun reSelected(holder: T)
    }
}


