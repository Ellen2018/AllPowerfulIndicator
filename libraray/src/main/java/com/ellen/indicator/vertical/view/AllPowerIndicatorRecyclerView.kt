package com.ellen.indicator.vertical.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.ellen.indicator.vertical.AllPowerIndicatorAdapter
import com.ellen.indicator.vertical.AllPowerfulIndicator

internal class AllPowerIndicatorRecyclerView : RecyclerView {

    lateinit var allPowerfulIndicator: AllPowerfulIndicator

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (!allPowerfulIndicator.isFixedReset && allPowerfulIndicator.mode == AllPowerfulIndicator.Mode.FIXED) {
            val viewGroup = allPowerfulIndicator.parent as ViewGroup
            val marginWidth =
                allPowerfulIndicator.marginLeft + allPowerfulIndicator.marginRight + allPowerfulIndicator.paddingLeft + allPowerfulIndicator.paddingRight
            val marginHeight =
                allPowerfulIndicator.marginTop + allPowerfulIndicator.marginBottom + allPowerfulIndicator.paddingTop + allPowerfulIndicator.paddingBottom
            val verticalIndicatorAdapter = adapter as AllPowerIndicatorAdapter<*>
            verticalIndicatorAdapter.width = viewGroup.width - marginWidth
            verticalIndicatorAdapter.height = viewGroup.height - marginHeight
            allPowerfulIndicator.isFixedReset = true
            adapter?.notifyDataSetChanged()
        } else {
            if (allPowerfulIndicator.isFirstDraw) {
                //调整追踪View
                allPowerfulIndicator.isFirstDraw = false
                adapter?.notifyDataSetChanged()
            }
        }
    }

}