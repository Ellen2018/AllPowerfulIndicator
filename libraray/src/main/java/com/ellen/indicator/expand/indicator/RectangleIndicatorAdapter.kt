package com.ellen.indicator.expand.indicator

import android.view.View
import com.ellen.indicator.BaseIndicatorViewHolder
import com.ellen.libraray.R

/**
 * 短横线指示器
 */
class RectangleIndicatorAdapter :
    BaseIndicatorViewPagerAdapter<RectangleIndicatorIndicatorViewHolder> {

    private var unSelectColor:Int = 0
    private var selectColor:Int = 0

    constructor(selectColor:Int, unSelectColor:Int){
        this.viewPager = viewPager
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
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