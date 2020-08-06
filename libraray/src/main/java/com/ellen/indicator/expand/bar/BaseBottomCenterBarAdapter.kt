package com.ellen.indicator.expand.bar

import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.BaseViewHolder
import com.ellen.libraray.R
import com.google.android.material.tabs.TabLayout

/**
 * 可完成虾米音乐底部导航栏效果
 */
abstract class BaseBottomBarAdapter<C : BaseViewHolder, N : BaseViewHolder> :
    Adapter<BaseViewHolder>() {

    var animResource:Int? = R.anim.scale
    private var ago: Int = 0
    protected var currentPosition = 0
    private var isSelectCenter = false
    var onTabSelectListener: OnTabSelectListener<C, N>? = null
    private var centerViewHolder:C? = null

    //避免首次进入时触发reset 0 的bug
    private var isFirst = false
    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            if(!isFirst){
                isFirst = !isFirst
            }else {
                if(centerViewHolder != null) {
                    if (position >= getCenterPosition()) {
                        allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position + 1))
                    } else {
                        allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                    }
                }else{
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(position))
                }
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

    override fun showContent(holder: BaseViewHolder) {
        if (getItemType(holder.position) == 0) {
            if(centerViewHolder != null) {
                if (holder.position > getCenterPosition()) {
                    showContentNormal(holder.position - 1, holder as N)
                } else {
                    showContentNormal(holder.position, holder as N)
                }
            }else{
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
            centerViewHolder!!
        }
    }

    override fun onTabReselected(holder: BaseViewHolder) {
        //动画效果
        if(animResource != null) {
            val mAnimation =
                animResource?.let { AnimationUtils.loadAnimation(allPowerIndicator.context, it) }
            holder.itemView?.startAnimation(mAnimation)
        }

        if(centerViewHolder != null) {
            if (holder.position > getCenterPosition()) {
                onTabSelectListener?.onTabReselected(holder.position - 1, holder as N)
            } else {
                onTabSelectListener?.onTabReselected(holder.position, holder as N)
            }
        }else{
            onTabSelectListener?.onTabReselected(holder.position, holder as N)
        }
    }

    /**
     * 它优先级高于onTabSelected()
     */
    override fun onTabUnselected(holder: BaseViewHolder) {
        //1
        if(centerViewHolder != null){
            if(getItemType(allPowerIndicator.selectedTabPosition) == 1){
                ago = holder.position
                return
            }
        }
        if (getItemType(holder.position) == 0) {
            if(centerViewHolder != null) {
                if (holder.position > getCenterPosition()) {
                    onTabSelectListener?.onTabUnselected(holder.position - 1, holder as N)
                } else {
                    onTabSelectListener?.onTabUnselected(holder.position, holder as N)
                }
            }else{
                onTabSelectListener?.onTabUnselected(holder.position, holder as N)
            }
            ago = holder.position
        }
    }

    override fun onTabSelected(holder: BaseViewHolder) {
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
                holder.itemView?.startAnimation(mAnimation)
            }
        }
        if (getItemType(holder.position) == 1) {
            onTabSelectListener?.onCenterTabListener(holder as C)
            isSelectCenter = true
            allPowerIndicator.selectTab(allPowerIndicator.getTabAt(ago))
        } else if (getItemType(holder.position) == 0) {
            var truePosition = -1
            currentPosition = holder.position
            if(centerViewHolder != null) {
                if (holder.position > getCenterPosition()) {
                    truePosition = holder.position - 1
                    onTabSelectListener?.onTabSelected(holder.position - 1, holder as N)
                } else {
                    truePosition = holder.position
                    onTabSelectListener?.onTabSelected(holder.position, holder as N)
                }
            }else{
                truePosition = holder.position
                onTabSelectListener?.onTabSelected(holder.position, holder as N)
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

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        centerViewHolder = getCenterViewHolder()
        val itemSize = getItemSize()
        for (position in 0 until itemSize) {
            val tab = allPowerIndicator.newTab()
            allPowerIndicator.addTab(tab)
        }
        if (viewPager2 != null) {
            viewPager2?.registerOnPageChangeCallback(onPageChangeCallback)
        } else this.viewPager?.addOnPageChangeListener(onPagerChangeCallbackV1)
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
}