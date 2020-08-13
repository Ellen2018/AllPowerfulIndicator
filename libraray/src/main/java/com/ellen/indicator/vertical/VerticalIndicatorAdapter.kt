package com.ellen.indicator.vertical

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class VerticalIndicatorAdapter<T : BaseVerticalIndicatorViewHolder>(var adapter: Adapter<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectStatus: SelectStatus = SelectStatus()
    var onItemClickListener: OnItemClickListener? = null

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapter.getViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return adapter.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderT: T = holder as T
        adapter.showContent(position, holderT)
        if (position == selectStatus.selectedPosition) {
            adapter.selectedStatus(position, holderT)
        } else {
            adapter.unSelectedStatus(position, holderT)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onClickItem(position,holderT)
        }
    }

}

internal class SelectStatus {
    var selectedPosition = 0
}

internal interface OnItemClickListener {
    fun <T : BaseVerticalIndicatorViewHolder> onClickItem(position: Int, holder: T)
}