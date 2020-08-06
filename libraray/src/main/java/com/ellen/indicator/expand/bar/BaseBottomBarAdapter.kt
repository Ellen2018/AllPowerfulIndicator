package com.ellen.indicator.expand.bar

import com.ellen.indicator.BaseViewHolder

/**
 * 不带中间控件的底部导航栏
 */
abstract class BaseBottomBarAdapter<N : BaseViewHolder> : BaseBottomCenterBarAdapter<BaseViewHolder,N>(){

    final override fun getCenterViewHolder(): BaseViewHolder? {
        return null
    }

    final override fun initCenterTab(holder: BaseViewHolder) {

    }

    final override fun showContentCenter(holder: BaseViewHolder) {

    }
}