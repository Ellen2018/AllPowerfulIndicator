package com.ellen.allpowerfulindicator.atuobottombar

import android.graphics.Color
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.indicator.horizontal.expand.bottombar.BaseBottomCenterBarAdapter

/**
 * 这种方式也能完成自定义底部导航栏
 */
class AutoBottomBarAdapter2 : BaseBottomCenterBarAdapter<BaseHorizontalIndicatorViewHolder,AutoBottomBarAdapter.AutoBottomViewHolderHorizontal>(){

    override fun getCenterViewHolder(): BaseHorizontalIndicatorViewHolder? {
        return null
    }

    override fun getNormalViewHolder(): AutoBottomBarAdapter.AutoBottomViewHolderHorizontal {
        return AutoBottomBarAdapter.AutoBottomViewHolderHorizontal(R.layout.view_auto_bottom_tab)
    }

    override fun initCenterTab(holderHorizontal: BaseHorizontalIndicatorViewHolder) {

    }

    override fun initNormalTab(holder: AutoBottomBarAdapter.AutoBottomViewHolderHorizontal) {
        holder.tv.setTextColor(Color.GRAY)
    }

    override fun showContentCenter(holderHorizontal: BaseHorizontalIndicatorViewHolder) {

    }

    override fun showContentNormal(
        truePosition: Int,
        holder: AutoBottomBarAdapter.AutoBottomViewHolderHorizontal
    ) {

    }

    override fun selectedStatus(holderHorizontal: BaseHorizontalIndicatorViewHolder) {
        if(holderHorizontal is AutoBottomBarAdapter.AutoBottomViewHolderHorizontal) {
            holderHorizontal.tv.setTextColor(Color.BLUE)
        }
    }

    override fun unSelectedStatus(holderHorizontal: BaseHorizontalIndicatorViewHolder) {
        if(holderHorizontal is AutoBottomBarAdapter.AutoBottomViewHolderHorizontal) {
            holderHorizontal.tv.setTextColor(Color.GRAY)
        }
    }

    override fun reSelectedStatus(holderHorizontal: BaseHorizontalIndicatorViewHolder) {
        if(holderHorizontal is AutoBottomBarAdapter.AutoBottomViewHolderHorizontal) {
            holderHorizontal.tv.setTextColor(Color.BLUE)
        }
    }
}