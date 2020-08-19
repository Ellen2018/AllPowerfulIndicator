package com.ellen.indicator.test.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
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
            if (adapter.allPowerfulIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
                val layoutParams = holder.itemView.layoutParams as LayoutParams
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL
                holder.itemView.layoutParams = layoutParams
            } else {
                val layoutParams = holder.itemView.layoutParams as LayoutParams
                layoutParams.gravity = Gravity.CENTER_VERTICAL
                holder.itemView.layoutParams = layoutParams
            }
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
                        firstDrawCompete()
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

    fun firstDrawCompete() {
        if (adapter.allPowerfulIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
            clickVGravity(mapHolder[adapter.statusManager.selectPosition] as BaseIndicatorViewHolder)
        } else {
            clickHGravity(mapHolder[adapter.statusManager.selectPosition] as BaseIndicatorViewHolder)
        }

        val holder = mapHolder[adapter.statusManager.selectPosition]
        val afterFrameLayout = adapter.allPowerfulIndicator.baseLayoutManager.afterFrameLayout
        val afterView = adapter.allPowerfulIndicator.baseLayoutManager.afterView
        val beforeFrameLayout = adapter.allPowerfulIndicator.baseLayoutManager.beforeFrameLayout
        val beforeView = adapter.allPowerfulIndicator.baseLayoutManager.beforeView

        if (adapter.allPowerfulIndicator.orientation == AllPowerfulIndicator.Orientation.VERTICAL) {
            val layoutParamsAfter = afterView.layoutParams
            layoutParamsAfter.height = holder?.itemView?.height!!
            afterView.layoutParams = layoutParamsAfter
            afterFrameLayout.y = holder.itemView.y

            val layoutParamsBefore = beforeView.layoutParams
            layoutParamsBefore.height = holder.itemView.height
            beforeView.layoutParams = layoutParamsBefore
            beforeFrameLayout.y = holder.itemView.y
        } else {
            val layoutParamsAfter = afterView.layoutParams
            layoutParamsAfter.width = holder?.itemView?.width!!
            afterView.layoutParams = layoutParamsAfter
            afterFrameLayout.x = holder.itemView.x

            val layoutParamsBefore = beforeView.layoutParams
            layoutParamsBefore.width = holder.itemView.width
            beforeView.layoutParams = layoutParamsBefore
            beforeFrameLayout.x = holder.itemView.x
        }
    }
}