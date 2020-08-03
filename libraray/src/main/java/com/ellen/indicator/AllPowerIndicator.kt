package com.ellen.indicator

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.libraray.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 3种使用模式
 * 1.绑定ViewPager
 * 2.绑定ViewPager2
 * 3.自由模式
 */
class AllPowerIndicator : RelativeLayout, Indicator {

    val tabLayout: TabLayout =
        LayoutInflater.from(context).inflate(R.layout.view_tab_layout, this, true)
            .findViewById(R.id.tab_layout)

    var tabTraverse:TabTraverse? = null

    var itemTab: BaseItemTab? = null
    set(value) {
        field = value
        itemTab?.bindAllPowerIndicator(this)
    }

    fun setTabSelectListener(tabSelectListener: TabSelectListener){
        itemTab?.tabSelectListener = tabSelectListener
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        parsingAutoAttributes(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        parsingAutoAttributes(attributeSet)
    }


    /**
     * 解析属性
     */
    private fun parsingAutoAttributes(attributeSet: AttributeSet) {
        itemTab = BaseItemTab()
        val typeArray: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.AllPowerIndicator)
        val n = typeArray.indexCount
        for (i in 0..n) {
            when (val attr = typeArray.getIndex(i)) {
                R.styleable.AllPowerIndicator_itemLayout ->
                    itemTab?.itemLayout = typeArray.getResourceId(attr, -1)
                R.styleable.AllPowerIndicator_itemSpacing ->
                    itemTab?.itemSpacing = typeArray.getDimension(attr, 0F).toInt()
                R.styleable.AllPowerIndicator_itemMode ->
                    when(typeArray.getInt(attr,-1)){
                        0 -> {
                            itemTab?.itemMode = Mode.MODE_SCROLLABLE
                        }
                        1 -> {
                            itemTab?.itemMode = Mode.MODE_FIXED
                        }
                        2 -> {
                            itemTab?.itemMode = Mode.MODE_AUTO
                        }
                        else -> {
                            itemTab?.itemMode = Mode.MODE_FIXED
                        }
                    }
            }
        }
        itemTab?.bindAllPowerIndicator(this)
    }

    override fun select(position: Int) {
        tabLayout.getTabAt(position)?.select()
    }

    override fun bindViewPager(viewpager: ViewPager) {
        var isReset = false
        tabLayout.setupWithViewPager(viewpager)
        val count: Int? = viewpager.adapter?.count
        //这里双感叹号如何去掉
        for (position in 0..count!!) {
            val tab = tabLayout.getTabAt(position)
            itemTab?.itemLayout?.let { tab?.setCustomView(it) }
            tab?.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
            tab?.customView?.let { tabTraverse?.settingTab(tab, position, it) }
            if (position == 0) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabSelected(0, it) }
            }

            //重新调整TabLayout的大小
            if (!isReset) {
                val layoutParams = tabLayout.layoutParams
                layoutParams.height = tab?.customView?.layoutParams?.height!!
                tabLayout.layoutParams = layoutParams
                isReset = true
            }

        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabReselected(tab.position, it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabSelected(tab.position, it) }
            }
        })
        tabLayout.getTabAt(0)?.select()
    }

    override fun bindViewPager2(viewPager2: ViewPager2) {
        var isReset = false
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            itemTab?.itemLayout?.let { tab.setCustomView(it) }
            tab.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
            tab.customView?.let { tabTraverse?.settingTab(tab, position, it) }
            if (position == 0) {
                tab.customView?.let { itemTab?.tabSelectListener?.onTabSelected(0, it) }
            }

            //重新调整TabLayout的大小
            if (!isReset) {
                val layoutParams = tabLayout.layoutParams
                layoutParams.height = tab.customView?.layoutParams?.height!!
                tabLayout.layoutParams = layoutParams
                isReset = true
            }

        }.attach()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabReselected(tab.position, it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let { itemTab?.tabSelectListener?.onTabSelected(tab.position, it) }
            }
        })
        tabLayout.getTabAt(0)?.select()
    }

    /**
     * 一般情况下不使用此Api
     * 少数情况下会使用到
     * 比如：绑定RecycleView（例如标签索引效果）
     */
    override fun addTabs(itemTab: BaseItemTab, count: Int) {
        this.itemTab = itemTab
        var isReset = false
        for (position in 0 until count) {
            val tab = tabLayout.newTab()
            tab.setCustomView(itemTab.itemLayout)
            tabLayout.addTab(tab)
            tab.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
            tab.customView?.let { tabTraverse?.settingTab(tab, position, it) }
            if (position == 0) {
                tab.customView?.let { itemTab?.tabSelectListener?.onTabSelected(0, it) }
            }

            //重新调整TabLayout的大小
            if (!isReset) {
                val layoutParams = tabLayout.layoutParams
                layoutParams.height = tab.customView?.layoutParams?.height!!
                tabLayout.layoutParams = layoutParams
                isReset = true
            }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    itemTab.tabSelectListener?.onTabReselected(
                        tab.position,
                        it
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    itemTab.tabSelectListener?.onTabUnselected(
                        tab.position,
                        it
                    )
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    itemTab.tabSelectListener?.onTabSelected(
                        tab.position,
                        it
                    )
                }
            }
        })

    }

    /**
     * 动态的添加
     */
    override fun addTab(layoutId:Int) {

    }

    override fun getItemViewAt(position: Int): View? {
       return tabLayout.getTabAt(position)?.customView
    }

}

private interface Indicator {
    fun select(position: Int)
    fun bindViewPager(viewpager: ViewPager)
    fun bindViewPager2(viewPager2: ViewPager2)
    fun addTabs(itemTab: BaseItemTab,count:Int)
    fun addTab(layoutId: Int)
    fun getItemViewAt(position: Int):View?
}

interface TabTraverse{
    fun settingTab(tab:TabLayout.Tab,position: Int,itemView:View)
}
