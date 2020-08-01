package com.ellen.indicator

import android.view.View

open class ItemTab {
    var itemLayout: Int = 0
    var itemSpacing: Int = 0
    var itemMode:Mode = Mode.MODE_FIXED
    var tabSelectListener:TabSelectListener? = null
    lateinit var allPowerIndicator:AllPowerIndicator
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