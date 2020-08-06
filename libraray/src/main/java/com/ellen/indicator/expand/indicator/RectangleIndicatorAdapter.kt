package com.ellen.indicator.expand.indicator

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.BaseViewHolder
import com.ellen.libraray.R

/**
 * 短横线指示器
 */
class RectangleIndicatorAdapter :
    BaseIndicatorViewPagerAdapter<RectangleIndicatorViewHolder> {

    private var unSelectColor:Int = 0
    private var selectColor:Int = 0

    constructor(selectColor:Int, unSelectColor:Int){
        this.viewPager = viewPager
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
    }

    override fun getViewHolder(viewType: Int): RectangleIndicatorViewHolder {
        return RectangleIndicatorViewHolder(
            R.layout.item_tab_layout_rectangle_view
        )
    }

    override fun initTab(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun selectedStatus(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }

    override fun unSelectedStatus(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun reSelectedStatus(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }
}

class RectangleIndicatorViewHolder(layoutId:Int) : BaseViewHolder(layoutId){

    lateinit var view:View

    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}