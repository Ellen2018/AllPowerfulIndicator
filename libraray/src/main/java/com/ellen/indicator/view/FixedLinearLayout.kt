package com.ellen.indicator.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerfulIndicator

internal class FixedLinearLayout : LinearLayout{

    lateinit var fixedManger: FixedManger<*>
    lateinit var adapter: Adapter<BaseIndicatorViewHolder>
    private var mapHolder: MutableMap<Int, BaseIndicatorViewHolder> = HashMap()

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    fun <T : BaseIndicatorViewHolder> onAttachAdapter(adapter: Adapter<T>){
        this.adapter = adapter as Adapter<BaseIndicatorViewHolder>
        for(position in 0 until adapter.trueCount){
            val viewType = adapter.getItemType(position)
            val holder: T = adapter.getViewHolder(this, viewType)
            mapHolder[position] = holder
            if(adapter.allPowerfulIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
                val layoutParams = holder.itemView.layoutParams
                val trueLayoutParams = LayoutParams(layoutParams.width, 0, 1f)
                trueLayoutParams.gravity = Gravity.CENTER_HORIZONTAL
                holder.itemView.layoutParams = trueLayoutParams
            }else{
                val layoutParams = holder.itemView.layoutParams
                val trueLayoutParams = LayoutParams(0, layoutParams.height, 1f)
                trueLayoutParams.gravity = Gravity.CENTER_VERTICAL
                holder.itemView.layoutParams = trueLayoutParams
            }
            this.addView(holder.itemView)
            adapter.showContent(position,holder)
            if (holder.isResponseStatus(position)) {
                if (position == adapter.statusManager.selectPosition) {
                    //选择状态
                    adapter.selectedStatus(adapter.outStatusPosition(position), holder)
                } else {
                    //未选中状态
                    adapter.unSelectedStatus(adapter.outStatusPosition(position), holder)
                }
            }
            holder.itemView.setOnClickListener {
                if (position == adapter.statusManager.selectPosition) {
                    //重选状态
                    for (onTabClickListener in adapter.onTabClickListenerList) {
                        onTabClickListener.onTabReSelectedClick(
                            adapter.outStatusPosition(position),
                            holder
                        )
                    }
                    adapter.reSelectedStatus(adapter.outStatusPosition(position), holder)
                } else {
                    if (holder.isResponseStatus(position)) {
                        //未选状态事件触发
                        for (onTabClickListener in adapter.onTabClickListenerList) {
                            onTabClickListener.onTabUnSelectedClick(
                                adapter.outStatusPosition(adapter.statusManager.selectPosition),
                                holder
                            )
                        }
                        adapter.statusManager.selectPosition = position
                        notifyDataSetChanged()
                        //选中状态事件触发
                        for (onTabClickListener in adapter.onTabClickListenerList) {
                            onTabClickListener.onTabSelectedClick(
                                adapter.outStatusPosition(
                                    position
                                ), holder
                            )
                        }
                    } else {
                        //无状态点击
                        for (onTabClickListener in adapter.onTabClickListenerList) {
                            onTabClickListener.onNoStatusTabClick(
                                adapter.outNoStatusPosition(position), holder
                            )
                        }
                    }
                }
            }
        }
    }

    internal fun notifyDataSetChanged() {
        //更新内容
        //更新选择的状态
        for (position in 0 until adapter.trueCount) {
            val holder = mapHolder[position]!!
            adapter.showContent(position, holder)
            if (holder.isResponseStatus(position)) {
                if (position == adapter.statusManager.selectPosition) {
                    //选择状态
                    adapter.selectedStatus(adapter.outStatusPosition(position), holder)
                } else {
                    //未选中状态
                    adapter.unSelectedStatus(adapter.outStatusPosition(position), holder)
                }
            }
        }

    }

    internal fun selectPosition(position: Int, isResponseEvent: Boolean = true) {
        //未选状态事件触发
        if (isResponseEvent) {
            for (onTabClickListener in adapter.onTabClickListenerList) {
                mapHolder[adapter.statusManager.selectPosition]?.let {
                    onTabClickListener.onTabUnSelectedClick(
                        adapter.outStatusPosition(adapter.statusManager.selectPosition),
                        it
                    )
                }
            }
        }
        adapter.statusManager.selectPosition = adapter.inStatusPosition(position)
        notifyDataSetChanged()
        //选中状态事件触发
        if (isResponseEvent) {
            for (onTabClickListener in adapter.onTabClickListenerList) {
                mapHolder[adapter.statusManager.selectPosition]?.let {
                    onTabClickListener.onTabSelectedClick(
                        adapter.outStatusPosition(adapter.statusManager.selectPosition),
                        it
                    )
                }
            }
        }
    }
}