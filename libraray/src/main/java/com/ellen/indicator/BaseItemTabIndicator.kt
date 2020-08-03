package com.ellen.indicator

import android.graphics.Color

open class BaseItemTabIndicator : BaseItemTab(){

    init {
        itemSpacing = 0
        itemMode = Mode.MODE_FIXED
        tabRippleColor = Color.TRANSPARENT
    }

}