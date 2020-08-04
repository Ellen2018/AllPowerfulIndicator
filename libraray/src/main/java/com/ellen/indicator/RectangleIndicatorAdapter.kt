package com.ellen.indicator

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.libraray.R

/**
 * 短横线指示器
 */
class RectangleIndicatorAdapter : BaseIndicatorViewPagerAdapter<RectangleIndicatorViewHolder>{

    private var unSelectColor:Int = 0
    private var selectColor:Int = 0

    constructor(viewPager: ViewPager, selectColor:Int, unSelectColor:Int) : super(viewPager) {
        this.viewPager = viewPager
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
    }

    constructor(viewPager2: ViewPager2, selectColor:Int, unSelectColor:Int) : super(viewPager2) {
        this.viewPager2 = viewPager2
        this.unSelectColor = unSelectColor
        this.selectColor = selectColor
    }

    override fun getViewHolder(viewType: Int): RectangleIndicatorViewHolder {
        return RectangleIndicatorViewHolder(R.layout.item_tab_layout_rectangle_view)
    }

    override fun onTabReselected(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }

    override fun onTabUnselected(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(unSelectColor)
    }

    override fun onTabSelected(holder: RectangleIndicatorViewHolder) {
        holder.view.setBackgroundColor(selectColor)
    }
}

class RectangleIndicatorViewHolder(layoutId:Int) : BaseViewHolder(layoutId){

    lateinit var view:View

    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}