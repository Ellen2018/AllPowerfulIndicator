package com.ellen.indicator.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.ellen.indicator.Adapter
import com.ellen.indicator.AllPowerfulIndicator
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
        val x = holder.itemView.x.toInt()
        var y = when (adapter.allPowerfulIndicator.clickGravity) {
            AllPowerfulIndicator.ClickGravity.TOP ->
                holder.itemView.y.toInt() - (adapter.allPowerfulIndicator.clickD * holder.itemView.height)
            AllPowerfulIndicator.ClickGravity.CENTER ->
                holder.itemView.y.toInt() - parentHeight / 2 + holder.itemView.height / 2
            AllPowerfulIndicator.ClickGravity.BOTTOM ->
                holder.itemView.y.toInt() - parentHeight + holder.itemView.height +
                        (adapter.allPowerfulIndicator.clickD * holder.itemView.height)
            else -> {
                holder.itemView.y.toInt() - parentHeight / 2 + holder.itemView.height / 2
            }
        }
        vManager.vScrollView.smoothScrollTo(x, y)
    }

    private fun <T : BaseIndicatorViewHolder> clickHGravity(holder: T) {
        val hManage = adapter.allPowerfulIndicator.baseLayoutManager as HManage<T>
        val y = holder.itemView.y
        val x = when (adapter.allPowerfulIndicator.clickGravity) {
            AllPowerfulIndicator.ClickGravity.LEFT ->
                holder.itemView.x.toInt() - (adapter.allPowerfulIndicator.clickD * holder.itemView.width)
            AllPowerfulIndicator.ClickGravity.CENTER ->
                holder.itemView.x.toInt() - parentWidth / 2 + holder.itemView.width / 2
            AllPowerfulIndicator.ClickGravity.RIGHT ->
                holder.itemView.x.toInt() - parentWidth + holder.itemView.width +
                        (adapter.allPowerfulIndicator.clickD * holder.itemView.width)
            else ->
                holder.itemView.x.toInt() - parentWidth / 2 + holder.itemView.width / 2
        }
        hManage.hScrollView.smoothScrollTo(x, y.toInt())
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
        firstDrawCompete()
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

            val valueAnimatorAfter = ValueAnimator.ofFloat(afterFrameLayout.y, holder?.itemView?.y!!)
            valueAnimatorAfter.duration = 100
            valueAnimatorAfter.addUpdateListener {
                val currentX = it.animatedValue as Float
                afterFrameLayout.y = currentX
            }
            valueAnimatorAfter.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    val layoutParamsAfter = afterView.contentView.layoutParams
                    layoutParamsAfter.height = holder.itemView.height
                    afterView.contentView.layoutParams = layoutParamsAfter
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            valueAnimatorAfter.start()

            val valueAnimatorBefore = ValueAnimator.ofFloat(beforeFrameLayout.y, holder.itemView.y)
            valueAnimatorBefore.duration = 100
            valueAnimatorBefore.addUpdateListener {
                val currentX = it.animatedValue as Float
                beforeFrameLayout.y = currentX
            }
            valueAnimatorAfter.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    val layoutParamsBefore = beforeView.contentView.layoutParams
                    layoutParamsBefore.height = holder.itemView.height
                    beforeView.contentView.layoutParams = layoutParamsBefore
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            valueAnimatorBefore.start()
        } else {
            val layoutParamsAfter = afterView.contentView.layoutParams
            layoutParamsAfter.width = holder?.itemView?.width!!
            afterView.contentView.layoutParams = layoutParamsAfter

            //属性动画
            val valueAnimatorAfter = ValueAnimator.ofFloat(afterFrameLayout.x, holder.itemView.x)
            valueAnimatorAfter.duration = 100
            valueAnimatorAfter.addUpdateListener {
                val currentX = it.animatedValue as Float
                afterFrameLayout.x = currentX
            }
            valueAnimatorAfter.start()

            val layoutParamsBefore = beforeView.contentView.layoutParams
            layoutParamsBefore.width = holder.itemView.width
            beforeView.contentView.layoutParams = layoutParamsBefore

            val valueAnimatorBefore = ValueAnimator.ofFloat(beforeFrameLayout.x, holder.itemView.x)
            valueAnimatorBefore.duration = 100
            valueAnimatorBefore.addUpdateListener {
                val currentX = it.animatedValue as Float
                beforeFrameLayout.x = currentX
            }
            valueAnimatorBefore.start()
        }
    }
}