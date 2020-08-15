package com.ellen.indicator.vertical

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class AllPowerIndicatorAdapter<T : BaseIndicatorViewHolder>(
    var allPowerIndicator: AllPowerfulIndicator,
    var adapter: Adapter<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Volatile
    var selectStatus: SelectStatus = SelectStatus()
    var onItemClickListener: OnItemClickListener<T>? = null
    var width = 0
    var height = 0

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
        //适配Fixed模式
        if (allPowerIndicator.mode == AllPowerfulIndicator.Mode.FIXED && allPowerIndicator.isFixedReset) {
            val itemCount = adapter.getItemCount()
            if (allPowerIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
                val itemHeight = height / itemCount
                val layoutParams = holder.itemView.layoutParams
                layoutParams.height = itemHeight
                holder.itemView.layoutParams = layoutParams
            }else{
                val itemWidth = width/itemCount
                val layoutParams = holder.itemView.layoutParams
                layoutParams.width = itemWidth
                holder.itemView.layoutParams = layoutParams
            }
        }

        val holderT: T = holder as T
        adapter.showContent(position, holderT)
        if(holder.isResponseStatus(position)) {
            if (position == selectStatus.selectedPosition) {
                adapter.selectedStatus(adapter.outStatusPosition(position), holderT)
            } else {
                adapter.unSelectedStatus(adapter.outStatusPosition(position), holderT)
            }
        }
        holder.itemView.setOnClickListener {
            if(holder.isResponseStatus(position)){
                onItemClickListener?.onClickStatusItem(adapter.outStatusPosition(position), holderT)
            }else {
                onItemClickListener?.onClickNoStatusItem(position,holderT)
            }
        }
    }
}

internal class SelectStatus {
    var selectedPosition = 0
}

internal interface OnItemClickListener<T> {
    fun onClickStatusItem(position: Int, holder: T)
    fun onClickNoStatusItem(position: Int,holder: T)
}