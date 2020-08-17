package com.ellen.indicator.vertical

import android.util.Log
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
    var mandatorySize: Int? = null
    var mapHolder:MutableMap<Int,RecyclerView.ViewHolder> = HashMap()

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapter.getViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return if (mandatorySize != null) {
            mandatorySize!!
        } else {
            adapter.getItemCount()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(!mapHolder.containsKey(position)) {
            mapHolder[position] = holder
        }

        val holderT: T = holder as T
        adapter.showContent(position, holderT)
        if(holder.isResponseStatus(position)) {
            if (position == selectStatus.getPosition()) {

                if (allPowerIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
                   gravityVertical(position,holder)
                } else {
                   gravityHorizontal(position,holder)
                }
                adapter.selectedStatus(adapter.outStatusPosition(position), holderT)
            } else {
                adapter.unSelectedStatus(adapter.outStatusPosition(position), holderT)
            }
        }
        holder.itemView.setOnClickListener {
            if (holder.isResponseStatus(position)) {
                onItemClickListener?.onClickStatusItem(adapter.outStatusPosition(position), holderT)
            } else {
                onItemClickListener?.onClickNoStatusItem(position, holderT)
            }
        }

    }

    private fun gravityHorizontal(position: Int,holder: RecyclerView.ViewHolder){
        if(allPowerIndicator.mode == AllPowerfulIndicator.Mode.SCROLL) {
           var sWidth = when(allPowerIndicator.clickGravity){
               AllPowerfulIndicator.ClickGravity.CENTER-> {
                   (width) / 2 + mapHolder[position]?.itemView?.width!! / 2
               }
               AllPowerfulIndicator.ClickGravity.LEFT->{
                   (mapHolder[position]?.itemView?.width!!
                           + getWidth(position,allPowerIndicator.clickDeviation)).toInt()
               }
               AllPowerfulIndicator.ClickGravity.RIGHT->{
                   width - getWidth(position,allPowerIndicator.clickDeviation).toInt()
               }
               else -> {
                   (width) / 2 + mapHolder[position]?.itemView?.width!! / 2
               }
           }
            allPowerIndicator.hManager.horizontalScrollView.smoothScrollTo(
                getX(position).toInt() - sWidth,
                holder.itemView.y.toInt()
            )
        }
    }

    private fun gravityVertical(position: Int,holder: RecyclerView.ViewHolder){
        if(allPowerIndicator.mode == AllPowerfulIndicator.Mode.SCROLL) {
            var sHeight = when(allPowerIndicator.clickGravity){
                AllPowerfulIndicator.ClickGravity.CENTER-> {
                    (height) / 2 + mapHolder[position]?.itemView?.height!! / 2
                }
                AllPowerfulIndicator.ClickGravity.TOP->{
                    (mapHolder[position]?.itemView?.height!!
                            + getHeight(position,allPowerIndicator.clickDeviation)).toInt()
                }
                AllPowerfulIndicator.ClickGravity.BOTTOM->{
                    height - getHeight(position,allPowerIndicator.clickDeviation).toInt()
                }
                else -> {
                    (height) / 2 + mapHolder[position]?.itemView?.height!! / 2
                }
            }
            allPowerIndicator.vManager.verticalScrollView.smoothScrollTo(
                holder.itemView.x.toInt(),
                getY(position).toInt() - sHeight
            )
        }
    }

    private fun getY(position: Int):Float{
        var y = 0f
        for(index in 0..position){
            y += mapHolder[position]?.itemView?.height!!
        }
        return y
    }

    /**
     * 获取position之前count个的高度
     */
    private fun getHeight(position: Int,count:Int):Float{
        var y = 0F
        for(index in 0 until count){
            y += mapHolder[position - (index+1)]?.itemView?.height!!
        }
        return y
    }

    private fun getWidth(position: Int,count:Int):Float{
        var x = 0F
        for(index in 0 until count){
            x += mapHolder[position - (index+1)]?.itemView?.width!!
        }
        return x
    }

    private fun getX(position: Int):Float{
        var x = 0f
        for(index in 0..position){
            x += mapHolder[position]?.itemView?.width!!
        }
        return x
    }
}

internal class SelectStatus {
    var isPositiveDirection:Boolean? = null
    private var selectedPosition = 0

    fun setPosition(position: Int){
        isPositiveDirection = when {
            position > selectedPosition -> {
                true
            }
            position == selectedPosition -> {
                null
            }
            else -> {
                false
            }
        }
        this.selectedPosition = position
    }

    fun getPosition():Int{
        return selectedPosition
    }
}

internal interface OnItemClickListener<T> {
    fun onClickStatusItem(position: Int, holder: T)
    fun onClickNoStatusItem(position: Int,holder: T)
}