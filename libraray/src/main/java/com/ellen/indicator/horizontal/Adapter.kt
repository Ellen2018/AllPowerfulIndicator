package com.ellen.indicator.horizontal

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

abstract class Adapter<T : BaseHorizontalIndicatorViewHolder> {

    lateinit var allPowerIndicator: HorizontalIndicator
    var onTabSelectedListener: OnTabSelectedListener<T>? = null

    protected var viewPager:ViewPager? = null
    protected var viewPager2: ViewPager2? = null
    protected var isBindAllPowerIndicator = false
    internal lateinit var mode: Mode

    /**
     * tab的类型
     */
    abstract fun getItemType(position: Int): Int

    /**
     * 绑定Tab的ViewHolder
     */
    abstract fun getViewHolder(viewType: Int): T?

    /**
     * tab的个数get
     */
    open fun getItemSize(): Int{
        return when(mode){
            Mode.FREE ->0
            Mode.VIEW_PAGER -> allPowerIndicator.tabCount
            Mode.VIEW_PAGER2 -> allPowerIndicator.tabCount
        }
    }

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
    open fun bindLinkageView(){
        isBindAllPowerIndicator = true
        when(mode){
            Mode.FREE ->bindLinkageFree()
            Mode.VIEW_PAGER -> viewPager?.let { bindLinkageViewPager(it) }
            Mode.VIEW_PAGER2 -> viewPager2?.let { bindLinkageViewPager2(it) }
        }
    }

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

    internal open fun initComplete(){
        if(getItemSize() != 0){
            allPowerIndicator.selectTab(allPowerIndicator.getTabAt(0))
            selectedStatus(allPowerIndicator.getTabAt(0)?.customView?.tag as T)
        }
    }

    internal open fun isOriginal():Boolean{
        return false
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

    private fun bindLinkageViewPager(viewPager: ViewPager){
        allPowerIndicator.setupWithViewPager(viewPager)
    }

    private fun bindLinkageViewPager2(viewPager2: ViewPager2){
        viewPager2.let {
            TabLayoutMediator(allPowerIndicator, it) { _, _ ->}.attach()
        }
    }

    protected fun bindLinkageFree(){
        for (position in 0 until getItemSize()) {
            val tab = allPowerIndicator.newTab()
            allPowerIndicator.addTab(tab)
        }
    }

    interface OnTabSelectedListener<T : BaseHorizontalIndicatorViewHolder>{
        fun selected(holder: T)
        fun unSelected(holder: T)
        fun reSelected(holder: T)
    }

    enum class Mode{
        VIEW_PAGER,
        VIEW_PAGER2,
        FREE
    }
}


