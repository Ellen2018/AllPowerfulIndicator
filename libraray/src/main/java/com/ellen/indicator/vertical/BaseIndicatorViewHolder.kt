package com.ellen.indicator.vertical

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseIndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    /**
     * 返回值代表该ViewHolder是否具有状态
     * 无状态的ViewHolder不参与Status一切相关逻辑
     */
    open fun isResponseStatus(position:Int):Boolean{
        return true
    }
}