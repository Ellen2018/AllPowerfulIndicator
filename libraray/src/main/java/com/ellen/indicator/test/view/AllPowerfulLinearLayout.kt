package com.ellen.indicator.test.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ellen.indicator.test.Adapter
import com.ellen.indicator.test.AllPowerfulIndicator
import kotlin.collections.set

internal class AllPowerfulLinearLayout : LinearLayout {

    lateinit var adapter: Adapter<BaseIndicatorViewHolder>
    private var mapHolder: MutableMap<Int, BaseIndicatorViewHolder> = HashMap()
    var parentWidth: Int = 0
    var parentHeight = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    fun <T : BaseIndicatorViewHolder> onAttachAdapter(adapter: Adapter<T>) {
        this.adapter = adapter as Adapter<BaseIndicatorViewHolder>
        for (position in 0 until adapter.trueCount) {
            val viewType = adapter.getItemType(position)
            val holder: T = adapter.getViewHolder(this, viewType)
            mapHolder[position] = holder
            this.addView(holder.itemView)
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

                if (adapter.allPowerfulIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
                    clickVGravity(holder)
                } else {
                    clickHGravity(holder)
                }

            }
        }
    }

    private fun <T : BaseIndicatorViewHolder> clickVGravity(holder: T) {
        val vManager = adapter.allPowerfulIndicator.baseLayoutManager as VManager<T>
        when (adapter.allPowerfulIndicator.clickGravity) {
            AllPowerfulIndicator.ClickGravity.TOP ->
                vManager.vScrollView.smoothScrollTo(
                    holder.itemView.x.toInt(),
                    holder.itemView.y.toInt() - (adapter.allPowerfulIndicator.clickD * holder.itemView.height)
                )
            AllPowerfulIndicator.ClickGravity.CENTER ->
                vManager.vScrollView.smoothScrollTo(
                    holder.itemView.x.toInt(),
                    holder.itemView.y.toInt() - parentHeight / 2 + holder.itemView.height / 2
                )
            AllPowerfulIndicator.ClickGravity.BOTTOM -> vManager.vScrollView.smoothScrollTo(
                holder.itemView.x.toInt(),
                holder.itemView.y.toInt() - parentHeight + holder.itemView.height + (adapter.allPowerfulIndicator.clickD * holder.itemView.height)
            )
            else -> {
                vManager.vScrollView.smoothScrollTo(
                    holder.itemView.x.toInt(),
                    holder.itemView.y.toInt() - parentHeight / 2 + holder.itemView.height / 2
                )
            }
        }

    }

    private fun <T : BaseIndicatorViewHolder> clickHGravity(holder: T) {
        val hManage = adapter.allPowerfulIndicator.baseLayoutManager as HManage<T>
        when (adapter.allPowerfulIndicator.clickGravity) {
            AllPowerfulIndicator.ClickGravity.LEFT ->
                hManage.hScrollView.smoothScrollTo(
                    holder.itemView.x.toInt() - (adapter.allPowerfulIndicator.clickD * holder.itemView.width),
                    holder.itemView.y.toInt()
                )
            AllPowerfulIndicator.ClickGravity.CENTER ->
                hManage.hScrollView.smoothScrollTo(
                    holder.itemView.x.toInt() - parentWidth / 2 + holder.itemView.width / 2,
                    holder.itemView.y.toInt()
                )
            AllPowerfulIndicator.ClickGravity.RIGHT -> hManage.hScrollView.smoothScrollTo(
                holder.itemView.x.toInt() - parentWidth + holder.itemView.width + (adapter.allPowerfulIndicator.clickD * holder.itemView.width),
                holder.itemView.y.toInt()
            )
            else -> {
                hManage.hScrollView.smoothScrollTo(
                    holder.itemView.x.toInt() - parentWidth / 2 + holder.itemView.width / 2,
                    holder.itemView.y.toInt()
                )
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

    internal fun fixedAdjust(isV: Boolean) {
        for (position in 0 until adapter.trueCount) {
            if (isV) {
                val itemHeight: Float = parentHeight.toFloat() / adapter.trueCount.toFloat()
                val itemView = mapHolder[position]?.itemView
                val layoutParams = itemView?.layoutParams
                layoutParams?.height = itemHeight.toInt()
                itemView?.layoutParams = layoutParams
            } else {
                val itemWidth = parentWidth.toFloat() / adapter.trueCount.toFloat()
                val itemView = mapHolder[position]?.itemView
                val layoutParams = itemView?.layoutParams
                layoutParams?.width = itemWidth.toInt()
                itemView?.layoutParams = layoutParams
            }
        }
    }
}