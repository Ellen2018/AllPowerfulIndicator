package com.ellen.indicator.vertical.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseTrackView {
    internal var currentX:Float = 0F
    internal var currentY:Float = 0F
    lateinit var contentView: View

    internal fun initContentView(inflater: LayoutInflater,parent:ViewGroup){
        contentView = onCreateTrackView(inflater,parent)
    }

    abstract fun onCreateTrackView(inflater: LayoutInflater,parent:ViewGroup):View

}