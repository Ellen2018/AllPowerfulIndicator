package com.ellen.allpowerfulindicator.bottomnavigation

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = Color.RED
        holder.view.background = drawable

        when (holder.position){
            0-> {
                holder.iv.setImageResource(R.drawable.ic_news)
                holder.tvTitle.text = "新闻"
            }
            1-> {
                holder.iv.setImageResource(R.drawable.ic_video)
                holder.tvTitle.text = "视频"
            }
            2-> {
                holder.iv.setImageResource(R.drawable.ic_message)
                holder.tvTitle.text = "消息"
            }
            3-> {
                holder.iv.setImageResource(R.drawable.ic_me)
                holder.tvTitle.text = "我"
            }
        }

    }

    override fun getViewHolder(viewType: Int): BottomViewHolder {
        return BottomViewHolder(R.layout.item_bottom_navigation_bar)
    }

    override fun onTabReselected(holder: BottomViewHolder) {
        Toast.makeText(holder.itemView?.context, "重选:${holder.position}", Toast.LENGTH_SHORT).show()
    }

    override fun onTabUnselected(holder: BottomViewHolder) {
        holder.tvTitle.setTextColor(Color.GRAY)
        holder.iv.imageTintList = ColorStateList.valueOf(Color.GRAY)
    }

    override fun onTabSelected(holder: BottomViewHolder) {
        holder.tvTitle.setTextColor(Color.BLUE)
        holder.iv.imageTintList = ColorStateList.valueOf(Color.BLUE)
    }

    override fun bindLinkageView(allPowerIndicator: AllPowerIndicator) {
        super.bindLinkageView(allPowerIndicator)
        allPowerIndicator.tabRippleColor = ColorStateList.valueOf(Color.GRAY)
    }

}

class BottomViewHolder(layoutId: Int) : BaseViewHolder(layoutId) {

    lateinit var tvTitle: TextView
    lateinit var view: View
    lateinit var iv: ImageView

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        view = itemView.findViewById(R.id.view_round)
        iv = itemView.findViewById(R.id.iv)
    }
}