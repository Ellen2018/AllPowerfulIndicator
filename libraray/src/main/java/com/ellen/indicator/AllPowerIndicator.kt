package com.ellen.indicator

import android.content.Context
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

    var itemTab: ItemTab? = null
    set(value) {
        field = value
        when (itemTab?.itemMode) {
            Mode.MODE_FIXED -> {
                tabLayout.tabMode = TabLayout.MODE_FIXED
            }
            Mode.MODE_SCROLLABLE -> {
                tabLayout.tabMode =TabLayout.MODE_SCROLLABLE
            }
            else -> {
                tabLayout.tabMode =TabLayout.MODE_AUTO
            }
        }
        itemTab?.allPowerIndicator = this
    }

    fun setTabSelectListener(tabSelectListener: TabSelectListener){
        itemTab?.tabSelectListener = tabSelectListener
    }

    init {
        tabLayout.setSelectedTabIndicator(null)
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
        itemTab = ItemTab()

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
                            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
                        }
                        1 -> {
                            itemTab?.itemMode = Mode.MODE_FIXED
                            tabLayout.tabMode = TabLayout.MODE_FIXED
                        }
                        2 -> {
                            itemTab?.itemMode = Mode.MODE_AUTO
                            tabLayout.tabMode = TabLayout.MODE_AUTO
                        }
                        else -> {
                            itemTab?.itemMode = Mode.MODE_FIXED
                            tabLayout.tabMode = TabLayout.MODE_FIXED
                        }
                    }
            }
        }
    }

    override fun select(position: Int) {
        tabLayout.getTabAt(position)?.select()
    }

    override fun bindViewPager(viewpager: ViewPager) {
        tabLayout.setupWithViewPager(viewpager)
        val count: Int? = viewpager.adapter?.count
        //这里双感叹号如何去掉
        for (i in 0..count!!) {
            val tab = tabLayout.getTabAt(i)
            itemTab?.itemLayout?.let { tab?.setCustomView(it) }
            tab?.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
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
    }

    override fun bindViewPager2(viewPager2: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            itemTab?.itemLayout?.let { tab.setCustomView(it) }
            tab.customView?.let { itemTab?.tabSelectListener?.onTabUnselected(tab.position, it) }
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
    }

}

private interface Indicator {
    fun select(position: Int)
    fun bindViewPager(viewpager: ViewPager)
    fun bindViewPager2(viewPager2: ViewPager2)
}

