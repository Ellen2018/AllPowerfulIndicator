package com.ellen.allpowerfulindicator.atuobottombar

import android.graphics.Color
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.ellen.indicator.horizontal.expand.bottombar.BaseBottomCenterBarAdapter

/**
 * 这种方式也能完成自定义底部导航栏
 */
class AutoBottomBarAdapter2 : BaseBottomCenterBarAdapter<BaseIndicatorViewHolder,AutoBottomBarAdapter.AutoBottomViewHolder>(){

    override fun getCenterViewHolder(): BaseIndicatorViewHolder? {
        return null
    }

    override fun getNormalViewHolder(): AutoBottomBarAdapter.AutoBottomViewHolder {
        return AutoBottomBarAdapter.AutoBottomViewHolder(R.layout.view_auto_bottom_tab)
    }

    override fun initCenterTab(holder: BaseIndicatorViewHolder) {

    }

    override fun initNormalTab(holder: AutoBottomBarAdapter.AutoBottomViewHolder) {
        holder.tv.setTextColor(Color.GRAY)
    }

    override fun showContentCenter(holder: BaseIndicatorViewHolder) {

    }

    override fun showContentNormal(
        truePosition: Int,
        holder: AutoBottomBarAdapter.AutoBottomViewHolder
    ) {

    }

    override fun selectedStatus(holder: BaseIndicatorViewHolder) {
        if(holder is AutoBottomBarAdapter.AutoBottomViewHolder) {
            holder.tv.setTextColor(Color.BLUE)
        }
    }

    override fun unSelectedStatus(holder: BaseIndicatorViewHolder) {
        if(holder is AutoBottomBarAdapter.AutoBottomViewHolder) {
            holder.tv.setTextColor(Color.GRAY)
        }
    }

    override fun reSelectedStatus(holder: BaseIndicatorViewHolder) {
        if(holder is AutoBottomBarAdapter.AutoBottomViewHolder) {
            holder.tv.setTextColor(Color.BLUE)
        }
    }
}