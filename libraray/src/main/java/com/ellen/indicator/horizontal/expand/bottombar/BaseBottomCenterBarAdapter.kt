package com.ellen.indicator.horizontal.expand.bottombar

import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.ellen.libraray.R
import com.google.android.material.tabs.TabLayout

/**
 * 带中间控件的底部导航栏
 */
abstract class BaseBottomCenterBarAdapter<C : BaseIndicatorViewHolder, N : BaseIndicatorViewHolder> :
    Adapter<BaseIndicatorViewHolder>() {

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
        onTabSelectedListener = object :OnTabSelectedListener<BaseIndicatorViewHolder>{
            override fun selected(holderIndicator: BaseIndicatorViewHolder) {
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
                        holderIndicator.itemView?.startAnimation(mAnimation)
                    }
                }
                if (getItemType(holderIndicator.position) == 1) {
                    onTabSelectListener?.onCenterTabListener(holderIndicator as C)
                    isSelectCenter = true
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(ago))
                } else if (getItemType(holderIndicator.position) == 0) {
                    var truePosition = -1
                    currentPosition = holderIndicator.position
                    if(centerViewHolder != null) {
                        if (holderIndicator.position > getCenterPosition()) {
                            truePosition = holderIndicator.position - 1
                            onTabSelectListener?.onTabSelected(holderIndicator.position - 1, holderIndicator as N)
                        } else {
                            truePosition = holderIndicator.position
                            onTabSelectListener?.onTabSelected(holderIndicator.position, holderIndicator as N)
                        }
                    }else{
                        truePosition = holderIndicator.position
                        onTabSelectListener?.onTabSelected(holderIndicator.position, holderIndicator as N)
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

            override fun unSelected(holderIndicator: BaseIndicatorViewHolder) {
                if(centerViewHolder != null){
                    if(getItemType(allPowerIndicator.selectedTabPosition) == 1){
                        ago = holderIndicator.position
                        return
                    }
                }
                if (getItemType(holderIndicator.position) == 0) {
                    if(centerViewHolder != null) {
                        if (holderIndicator.position > getCenterPosition()) {
                            onTabSelectListener?.onTabUnselected(holderIndicator.position - 1, holderIndicator as N)
                        } else {
                            onTabSelectListener?.onTabUnselected(holderIndicator.position, holderIndicator as N)
                        }
                    }else{
                        onTabSelectListener?.onTabUnselected(holderIndicator.position, holderIndicator as N)
                    }
                    ago = holderIndicator.position
                }
            }

            override fun reSelected(holderIndicator: BaseIndicatorViewHolder) {
                //动画效果
                if(animResource != null) {
                    val mAnimation =
                        animResource?.let { AnimationUtils.loadAnimation(allPowerIndicator.context, it) }
                    holderIndicator.itemView?.startAnimation(mAnimation)
                }

                //处理item点击事件
                if(centerViewHolder != null) {
                    if (holderIndicator.position > getCenterPosition()) {
                        onTabSelectListener?.onTabReselected(holderIndicator.position - 1, holderIndicator as N)
                    } else {
                        onTabSelectListener?.onTabReselected(holderIndicator.position, holderIndicator as N)
                    }
                }else{
                    onTabSelectListener?.onTabReselected(holderIndicator.position, holderIndicator as N)
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

    final override fun initTab(holderIndicator: BaseIndicatorViewHolder) {
        if(getItemType(holderIndicator.position) == 1){
            initCenterTab(holderIndicator as C)
        }else{
            initNormalTab(holderIndicator as N)
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

    override fun showContent(holderIndicator: BaseIndicatorViewHolder) {
        if (getItemType(holderIndicator.position) == 0) {
            if(centerViewHolder != null) {
                if (holderIndicator.position > getCenterPosition()) {
                    showContentNormal(holderIndicator.position - 1, holderIndicator as N)
                } else {
                    showContentNormal(holderIndicator.position, holderIndicator as N)
                }
            }else{
                showContentNormal(holderIndicator.position, holderIndicator as N)
            }
        } else {
            showContentCenter(holderIndicator as C)
        }
    }

    abstract fun showContentCenter(holder: C)
    abstract fun showContentNormal(truePosition: Int, holder: N)
    open fun getItemCount(): Int {
        return 0
    }

    override fun getViewHolder(viewType: Int): BaseIndicatorViewHolder {
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
        selectedStatus(allPowerIndicator.getTabAt(initPosition)?.customView?.tag as BaseIndicatorViewHolder)
    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setSelectedTabIndicator(null)
    }

    interface OnTabSelectListener<C : BaseIndicatorViewHolder, N : BaseIndicatorViewHolder> {
        fun onTabReselected(truePosition: Int, holder: N)
        fun onTabUnselected(truePosition: Int, holder: N)
        fun onTabSelected(truePosition: Int, holder: N)
        fun onCenterTabListener(holder: C)
    }
}

