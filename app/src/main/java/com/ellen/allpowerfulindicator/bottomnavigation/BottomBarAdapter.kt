package com.ellen.allpowerfulindicator.bottomnavigation

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.BaseIndicatorViewPagerAdapter
import com.ellen.indicator.BaseViewHolder

class BottomBarAdapter : BaseIndicatorViewPagerAdapter<BottomViewHolder> {

    var dataList: MutableList<Int>

    constructor(viewPager: ViewPager, dataList: MutableList<Int>) : super(viewPager) {
        this.dataList = dataList
    }

    constructor(viewPager2: ViewPager2, dataList: MutableList<Int>) : super(viewPager2) {
        this.dataList = dataList
    }

    override fun showContent(holder: BottomViewHolder) {
       holder.tvUpdate.text = "${dataList[holder.position]}"
    }

    override fun getViewHolder(viewType: Int): BottomViewHolder {
        return BottomViewHolder(R.layout.item_bottom_navigation_bar)
    }

    override fun onTabReselected(holder: BottomViewHolder) {

    }

    override fun onTabUnselected(holder: BottomViewHolder) {
        holder.tvTitle.setTextColor(Color.GRAY)
    }

    override fun onTabSelected(holder: BottomViewHolder) {
        holder.tvTitle.setTextColor(Color.BLUE)
    }

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        super.bindLinkageView(allPowerIndicator)
        allPowerIndicator.tabRippleColor = ColorStateList.valueOf(Color.GRAY)
    }

}

class BottomViewHolder(layoutId: Int) : BaseViewHolder(layoutId) {

    lateinit var tvTitle: TextView
    lateinit var tvUpdate: TextView

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvUpdate = itemView.findViewById(R.id.tv_update)
    }
}