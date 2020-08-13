package com.ellen.indicator.horizontal.expand.bottombar

import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.libraray.R
import com.google.android.material.tabs.TabLayout

/**
 * 带中间控件的底部导航栏
 */
abstract class BaseBottomCenterBarAdapter<C : BaseHorizontalIndicatorViewHolder, N : BaseHorizontalIndicatorViewHolder> :
    Adapter<BaseHorizontalIndicatorViewHolder>() {

    var animResource:Int? = R.anim.scale
    private var ago: Int = 0
    protected var currentPosition = 0
    private var isSelectCenter = false
    var onTabSelectListener: OnTabSelectListener<C, N>? = null
    private var centerViewHolder:C? = null

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            if (centerViewHolder != null) {
                if (position >= getCenterPosition()) {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position + 1))
                } else {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                }
            } else {
                allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
            }
        }
    }
    private val onPagerChangeCallbackV1 = object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            if(centerViewHolder != null) {
                if (position >= getCenterPosition()) {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position + 1))
                } else {
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                }
            } else {
                allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
            }
        }
    }

    abstract fun getCenterViewHolder(): C?
    abstract fun getNormalViewHolder(): N

    init {
        onTabSelectedListener = object :OnTabSelectedListener<BaseHorizontalIndicatorViewHolder>{
            override fun selected(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
                //2
                if(isSelectCenter){
                    isSelectCenter = false
                    return
                }else {
                    //动画效果
                    if (animResource != null) {
                        val mAnimation =
                            animResource?.let {
                                AnimationUtils.loadAnimation(
                                    allPowerIndicator.context,
                                    it
                                )
                            }
                        holderHorizontalIndicator.itemView?.startAnimation(mAnimation)
                    }
                }
                if (getItemType(holderHorizontalIndicator.position) == 1) {
                    onTabSelectListener?.onCenterTabListener(holderHorizontalIndicator as C)
                    isSelectCenter = true
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(ago))
                } else if (getItemType(holderHorizontalIndicator.position) == 0) {
                    var truePosition = -1
                    currentPosition = holderHorizontalIndicator.position
                    if(centerViewHolder != null) {
                        if (holderHorizontalIndicator.position > getCenterPosition()) {
                            truePosition = holderHorizontalIndicator.position - 1
                            onTabSelectListener?.onTabSelected(holderHorizontalIndicator.position - 1, holderHorizontalIndicator as N)
                        } else {
                            truePosition = holderHorizontalIndicator.position
                            onTabSelectListener?.onTabSelected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                        }
                    }else{
                        truePosition = holderHorizontalIndicator.position
                        onTabSelectListener?.onTabSelected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                    }

                    if (viewPager2 != null) {
                        viewPager2?.unregisterOnPageChangeCallback(onPageChangeCallback)
                        viewPager2?.currentItem = truePosition
                        viewPager2?.registerOnPageChangeCallback(onPageChangeCallback)
                    } else {
                        viewPager?.removeOnPageChangeListener(onPagerChangeCallbackV1)
                        viewPager?.currentItem = truePosition
                        viewPager?.addOnPageChangeListener(onPagerChangeCallbackV1)
                    }
                }
            }

            override fun unSelected(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
                if(centerViewHolder != null){
                    if(getItemType(allPowerIndicator.selectedTabPosition) == 1){
                        ago = holderHorizontalIndicator.position
                        return
                    }
                }
                if (getItemType(holderHorizontalIndicator.position) == 0) {
                    if(centerViewHolder != null) {
                        if (holderHorizontalIndicator.position > getCenterPosition()) {
                            onTabSelectListener?.onTabUnselected(holderHorizontalIndicator.position - 1, holderHorizontalIndicator as N)
                        } else {
                            onTabSelectListener?.onTabUnselected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                        }
                    }else{
                        onTabSelectListener?.onTabUnselected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                    }
                    ago = holderHorizontalIndicator.position
                }
            }

            override fun reSelected(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
                //动画效果
                if(animResource != null) {
                    val mAnimation =
                        animResource?.let { AnimationUtils.loadAnimation(allPowerIndicator.context, it) }
                    holderHorizontalIndicator.itemView?.startAnimation(mAnimation)
                }

                //处理item点击事件
                if(centerViewHolder != null) {
                    if (holderHorizontalIndicator.position > getCenterPosition()) {
                        onTabSelectListener?.onTabReselected(holderHorizontalIndicator.position - 1, holderHorizontalIndicator as N)
                    } else {
                        onTabSelectListener?.onTabReselected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                    }
                }else{
                    onTabSelectListener?.onTabReselected(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                }
            }
        }
    }

    protected open fun getCenterPosition(): Int {
        return getItemSize() / 2
    }

    override fun getItemType(position: Int): Int {
        if(centerViewHolder == null)return 0
        return  if(position == getCenterPosition()){
            1
        }else{
            0
        }
    }

    final override fun initTab(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
        if(getItemType(holderHorizontalIndicator.position) == 1){
            initCenterTab(holderHorizontalIndicator as C)
        }else{
            initNormalTab(holderHorizontalIndicator as N)
        }
    }

    abstract fun initCenterTab(holder: C)
    abstract fun initNormalTab(holder: N)

    override fun getItemSize(): Int {
        if(centerViewHolder != null) {
            return when {
                viewPager2 != null -> {
                    viewPager2?.adapter?.itemCount!! + 1
                }
                viewPager != null -> {
                    viewPager?.adapter?.count!! + 1
                }
                else -> {
                    getItemCount() + 1
                }
            }
        }else{
            return when {
                viewPager2 != null -> {
                    viewPager2?.adapter?.itemCount!!
                }
                viewPager != null -> {
                    viewPager?.adapter?.count!!
                }
                else -> {
                    getItemCount()
                }
            }
        }
    }

    override fun showContent(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
        if (getItemType(holderHorizontalIndicator.position) == 0) {
            if(centerViewHolder != null) {
                if (holderHorizontalIndicator.position > getCenterPosition()) {
                    showContentNormal(holderHorizontalIndicator.position - 1, holderHorizontalIndicator as N)
                } else {
                    showContentNormal(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
                }
            }else{
                showContentNormal(holderHorizontalIndicator.position, holderHorizontalIndicator as N)
            }
        } else {
            showContentCenter(holderHorizontalIndicator as C)
        }
    }

    abstract fun showContentCenter(holder: C)
    abstract fun showContentNormal(truePosition: Int, holder: N)
    open fun getItemCount(): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): BaseHorizontalIndicatorViewHolder {
        return if (viewType == 0) {
            getNormalViewHolder()
        } else {
            centerViewHolder!!
        }
    }

    override fun bindLinkageView() {
        centerViewHolder = getCenterViewHolder()
        bindLinkageFree()
        if (viewPager2 != null) {
            viewPager2?.registerOnPageChangeCallback(onPageChangeCallback)
        } else this.viewPager?.addOnPageChangeListener(onPagerChangeCallbackV1)
    }

    final override fun initComplete() {
        var initPosition = 0
        if(centerViewHolder != null && getCenterPosition() == 0) {
            initPosition = 1
        }
        allPowerIndicator.selectTab(allPowerIndicator.getTabAt(initPosition))
        selectedStatus(allPowerIndicator.getTabAt(initPosition)?.customView?.tag as BaseHorizontalIndicatorViewHolder)
    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setSelectedTabIndicator(null)
    }

    interface OnTabSelectListener<C : BaseHorizontalIndicatorViewHolder, N : BaseHorizontalIndicatorViewHolder> {
        fun onTabReselected(truePosition: Int, holder: N)
        fun onTabUnselected(truePosition: Int, holder: N)
        fun onTabSelected(truePosition: Int, holder: N)
        fun onCenterTabListener(holder: C)
    }
}

