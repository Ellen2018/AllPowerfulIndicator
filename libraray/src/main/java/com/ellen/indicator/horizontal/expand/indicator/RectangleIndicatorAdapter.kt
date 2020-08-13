package com.ellen.indicator.horizontal.expand.indicator

import android.view.View
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder
import com.ellen.libraray.R

/**
 * 短横线指示器
 */
class RectangleIndicatorAdapter (var selectColor: Int,var unSelectColor: Int)  :
    BaseIndicatorViewPagerAdapter<RectangleIndicatorIndicatorViewHolder>() {

    constructor(selectColor:Int, unSelectColor:Int,tabCount:Int):this(selectColor,unSelectColor){
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
        this.tabCount = tabCount
    }

    override fun getViewHolder(viewType: Int): RectangleIndicatorIndicatorViewHolder {
        return RectangleIndicatorIndicatorViewHolder(
            R.layout.item_tab_layout_rectangle_view
        )
    }

    override fun initTab(holder: RectangleIndicatorIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun selectedStatus(holder: RectangleIndicatorIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }

    override fun unSelectedStatus(holder: RectangleIndicatorIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun reSelectedStatus(holder: RectangleIndicatorIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }
}

class RectangleIndicatorIndicatorViewHolder(layoutId:Int) : BaseIndicatorViewHolder(layoutId){

    lateinit var view:View

    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}