package com.ellen.base.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<T:BaseViewHolder,D> : RecyclerView.Adapter<T>() {
     var onItemClickListener:OnItemClickListener<T,D>? = null
     var onItemLongClickListener: OnItemLongClickListener<T,D>? = null

    interface OnItemClickListener<T : BaseViewHolder,D> {
        fun onItemClick(holder:T,data : D,position:Int)
    }

    interface OnItemLongClickListener<T : BaseViewHolder,D>{
        fun onItemLongClick(holder:T,data : D,position: Int):Boolean
    }
}


