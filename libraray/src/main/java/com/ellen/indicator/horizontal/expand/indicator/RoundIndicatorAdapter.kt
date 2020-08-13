package com.ellen.indicator.horizontal.expand.indicator

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.ellen.libraray.R

/**
 * 圆点指示器
 */
class RoundIndicatorAdapter(var selectColor: Int,var unSelectColor: Int) :
    BaseIndicatorViewPagerAdapter<RoundIndicatorIndicatorViewHolder>() {

    constructor(selectColor:Int, unSelectColor:Int,tabCount:Int) : this(selectColor,unSelectColor) {
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
        this.tabCount = tabCount
    }

    override fun getViewHolder(viewType: Int): RoundIndicatorIndicatorViewHolder {
        return RoundIndicatorIndicatorViewHolder(R.layout.item_tab_layout_round_view)
    }

    override fun initTab(holder: RoundIndicatorIndicatorViewHolder) {
       unSelectedStatus(holder)
    }

    override fun selectedStatus(holder: RoundIndicatorIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }

    override fun unSelectedStatus(holder: RoundIndicatorIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = unSelectColor
        holder.view.background = drawable
    }

    override fun reSelectedStatus(holder: RoundIndicatorIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }
}


class RoundIndicatorIndicatorViewHolder(layoutId:Int): BaseIndicatorViewHolder(layoutId){
    lateinit var view:View
    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}