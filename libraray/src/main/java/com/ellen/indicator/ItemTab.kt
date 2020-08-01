package com.ellen.indicator

import android.content.Context
import android.view.View

open class ItemTab {
    var itemLayout: Int = 0
    var itemSpacing: Int = 0
    var itemMode:Mode = Mode.MODE_FIXED
    var tabSelectListener:TabSelectListener? = null
    lateinit var allPowerIndicator:AllPowerIndicator

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    open fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    open fun px2dip(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}

interface TabSelectListener {
    fun onTabReselected(position: Int, itemView: View)
    fun onTabUnselected(position: Int, itemView: View)
    fun onTabSelected(position: Int, itemView: View)
}

enum class Mode() {
    MODE_SCROLLABLE,
    MODE_FIXED,
    MODE_AUTO
}