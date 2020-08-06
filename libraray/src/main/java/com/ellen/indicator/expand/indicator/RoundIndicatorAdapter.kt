package com.ellen.indicator.expand.indicator

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.BaseViewHolder
import com.ellen.libraray.R

/**
 * 圆点指示器
 */
class RoundIndicatorAdapter(var selectColor: Int,var unSelectColor: Int) :
    BaseIndicatorViewPagerAdapter<RoundIndicatorViewHolder>() {

    override fun getViewHolder(viewType: Int): RoundIndicatorViewHolder {
        return RoundIndicatorViewHolder(R.layout.item_tab_layout_round_view)
    }

    override fun initTab(holder: RoundIndicatorViewHolder) {
       unSelectedStatus(holder)
    }

    override fun selectedStatus(holder: RoundIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }

    override fun unSelectedStatus(holder: RoundIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = unSelectColor
        holder.view.background = drawable
    }

    override fun reSelectedStatus(holder: RoundIndicatorViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = selectColor
        holder.view.background = drawable
    }
}


class RoundIndicatorViewHolder(layoutId:Int): BaseViewHolder(layoutId){
    lateinit var view:View
    override fun bindView(itemView: View) {
        view = itemView.findViewById(R.id.view)
    }
}