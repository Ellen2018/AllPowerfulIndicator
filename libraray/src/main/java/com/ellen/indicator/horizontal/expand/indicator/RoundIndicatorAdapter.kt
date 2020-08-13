package com.ellen.indicator.horizontal.expand.indicator

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.libraray.R

/**
 * 圆点指示器
 */
class RoundIndicatorAdapter(var selectColor: Int,var unSelectColor: Int) :
    BaseIndicatorViewPagerAdapter<RoundIndicatorHorizontalIndicatorViewHolder>() {

    constructor(selectColor:Int, unSelectColor:Int,tabCount:Int) : this(selectColor,unSelectColor) {
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
        this.tabCount = tabCount
    }

    override fun getViewHolder(viewType: Int): RoundIndicatorHorizontalIndicatorViewHolder {
        return RoundIndicatorHorizontalIndicatorViewHolder(R.layout.item_tab_layout_round_view)
    }

    override fun initTab(holder: RoundIndicatorHorizontalIndicatorViewHolder) {
       unSelectedStatus(holder)
    }

    override fun selectedStatus(holder: RoundIndicatorHorizontalIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }

    override fun unSelectedStatus(holder: RoundIndicatorHorizontalIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = unSelectColor
        holder.view.background = drawable
    }

    override fun reSelectedStatus(holder: RoundIndicatorHorizontalIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }
}


class RoundIndicatorHorizontalIndicatorViewHolder(layoutId:Int): BaseHorizontalIndicatorViewHolder(layoutId){
    lateinit var view:View
    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}