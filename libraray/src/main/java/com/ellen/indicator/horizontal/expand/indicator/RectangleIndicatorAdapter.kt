package com.ellen.indicator.horizontal.expand.indicator

import android.view.View
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.libraray.R

/**
 * 短横线指示器
 */
class RectangleIndicatorAdapter (var selectColor: Int,var unSelectColor: Int)  :
    BaseIndicatorViewPagerAdapter<RectangleIndicatorHorizontalIndicatorViewHolder>() {

    constructor(selectColor:Int, unSelectColor:Int,tabCount:Int):this(selectColor,unSelectColor){
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
        this.tabCount = tabCount
    }

    override fun getViewHolder(viewType: Int): RectangleIndicatorHorizontalIndicatorViewHolder {
        return RectangleIndicatorHorizontalIndicatorViewHolder(
            R.layout.item_tab_layout_rectangle_view
        )
    }

    override fun initTab(holder: RectangleIndicatorHorizontalIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun selectedStatus(holder: RectangleIndicatorHorizontalIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }

    override fun unSelectedStatus(holder: RectangleIndicatorHorizontalIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun reSelectedStatus(holder: RectangleIndicatorHorizontalIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }
}

class RectangleIndicatorHorizontalIndicatorViewHolder(layoutId:Int) : BaseHorizontalIndicatorViewHolder(layoutId){

    lateinit var view:View

    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}