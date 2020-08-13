package com.ellen.indicator.vertical

import android.view.ViewGroup

abstract class Adapter<T : BaseVerticalIndicatorViewHolder>{

    /**
     * item类型
     */
    abstract fun getItemType(position:Int):Int
    abstract fun getViewHolder(parent:ViewGroup,viewType:Int): T
    abstract fun getItemCount():Int
    abstract fun showContent(position:Int,holder:T)
    abstract fun selectedStatus(position:Int,holder: T)
    abstract fun unSelectedStatus(position:Int,holder: T)
    abstract fun reSelectedStatus(position:Int,holder: T)

}