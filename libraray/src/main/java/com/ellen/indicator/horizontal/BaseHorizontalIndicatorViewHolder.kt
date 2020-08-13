package com.ellen.indicator.horizontal

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHorizontalIndicatorViewHolder(var layoutId:Int){

    var position:Int = 0
    var viewType:Int = 0
    var itemView: View? = null

    abstract fun bindView(itemView:View)

}