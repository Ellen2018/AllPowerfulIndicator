package com.ellen.indicator

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

/**
 * 可完成虾米音乐底部导航栏效果
 */
abstract class BaseBottomBarAdapter<C : BaseViewHolder, N : BaseViewHolder> :
    Adapter<BaseViewHolder>() {

    private var ago: Int = 0

    var onTabSelectListener: OnTabSelectListener<C, N>? = null

    //避免首次进入时触发reset 0 的bug
    private var isFirst = false
    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            if(!isFirst){
                isFirst = !isFirst
            }else {
                if (position >= getItemSize() / 2) {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position + 1))
                } else {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                }
            }
        }

    }

    abstract fun getCenterViewHolder(): C
    abstract fun getNormalViewHolder(): N

    override fun getItemType(position: Int): Int {
        return if (getItemSize() % 2 == 0) {
            0
        } else {
            if (position == getItemSize() / 2) {
                1
            } else {
                0
            }
        }
    }

    override fun getItemSize(): Int {
        val viewPager2 = onTabSelectListener?.bindViewPager2()
        val viewPager1 = onTabSelectListener?.bindViewPager()
        return when {
            viewPager2 != null -> {
                viewPager2.adapter?.itemCount!! + 1
            }
            viewPager1 != null -> {
                viewPager1.adapter?.count!! + 1
            }
            else -> {
                getItemCount() + 1
            }
        }
    }

    override fun showContent(holder: BaseViewHolder) {
        if (getItemType(holder.position) == 0) {
            if (holder.position > getItemSize() / 2) {
                showContentNormal(holder.position - 1, holder as N)
            } else {
                showContentNormal(holder.position, holder as N)
            }
        } else {
            showContentCenter(holder as C)
        }
    }


    abstract fun showContentCenter(holder: C)
    abstract fun showContentNormal(truePosition: Int, holder: N)
    open fun getItemCount(): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): BaseViewHolder {
        return if (viewType == 0) {
            getNormalViewHolder()
        } else {
            getCenterViewHolder()
        }
    }

    override fun onTabReselected(holder: BaseViewHolder) {
        if (holder.position > getItemSize() / 2) {
            onTabSelectListener?.onTabReselected(holder.position - 1, holder as N)
        } else {
            onTabSelectListener?.onTabReselected(holder.position, holder as N)
        }
    }

    override fun onTabUnselected(holder: BaseViewHolder) {
        if (getItemType(holder.position) == 0) {
            if (holder.position > getItemSize() / 2) {
                onTabSelectListener?.onTabUnselected(holder.position - 1, holder as N)
            } else {
                onTabSelectListener?.onTabUnselected(holder.position, holder as N)
            }
            ago = holder.position
        }
    }

    override fun onTabSelected(holder: BaseViewHolder) {
        if (getItemType(holder.position) == 1) {
            onTabSelectListener?.onCenterTabListener(holder as C)
            allPowerIndicator.selectTab(allPowerIndicator.getTabAt(ago))
        } else if (getItemType(holder.position) == 0) {
            var truePosition = -1
            if (holder.position > getItemSize() / 2) {
                truePosition = holder.position - 1
                onTabSelectListener?.onTabSelected(holder.position - 1, holder as N)
            } else {
                truePosition = holder.position
                onTabSelectListener?.onTabSelected(holder.position, holder as N)
            }
            val viewPager2 = onTabSelectListener?.bindViewPager2()
            val viewPager = onTabSelectListener?.bindViewPager()
            if (viewPager2 != null) {
                viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback)
                viewPager2.currentItem = truePosition
                viewPager2.registerOnPageChangeCallback(onPageChangeCallback)
            } else {
                viewPager?.currentItem = truePosition
            }
        }
    }

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        val viewPager2 = onTabSelectListener?.bindViewPager2()
        var itemSize = getItemSize()
        for (position in 0 until itemSize) {
            val tab = allPowerIndicator.newTab()
            allPowerIndicator.addTab(tab)
        }
        if (viewPager2 != null) {
            viewPager2.registerOnPageChangeCallback(onPageChangeCallback)
        } else onTabSelectListener?.bindViewPager()?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position >= getItemSize() / 2) {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position + 1))
                } else {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                }
            }
        })
    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setSelectedTabIndicator(null)
    }
}

interface OnTabSelectListener<C : BaseViewHolder, N : BaseViewHolder> {
    fun onTabReselected(truePosition: Int, holder: N)
    fun onTabUnselected(truePosition: Int, holder: N)
    fun onTabSelected(truePosition: Int, holder: N)
    fun onCenterTabListener(holder: C)
    fun bindViewPager(): ViewPager? {
        return null
    }

    fun bindViewPager2(): ViewPager2? {
        return null
    }
}