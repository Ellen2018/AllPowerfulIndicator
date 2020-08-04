package com.ellen.indicator

import android.view.View

abstract class BaseViewHolder(var layoutId:Int) {

    var position:Int = 0
    var viewType:Int = 0
    var itemView: View? = null

    abstract fun bindView(itemView:View)

}