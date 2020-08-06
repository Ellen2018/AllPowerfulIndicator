package com.ellen.allpowerfulindicator.atuobottombar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseViewHolder
import com.ellen.indicator.expand.bar.BaseBottomBarAdapter

/**
 * 自定义底部导航栏(不带中间控件)
 */
class AutoBottomBarAdapter : BaseBottomBarAdapter<BottomViewHolder>(){

    init {
        onTabSelectListener = object : OnTabSelectListener<BaseViewHolder,BottomViewHolder>{
            override fun onTabReselected(truePosition: Int, holder: BottomViewHolder) {

            }

            override fun onTabUnselected(truePosition: Int, holder: BottomViewHolder) {
               holder.tv.setTextColor(Color.GRAY)
            }

            override fun onTabSelected(truePosition: Int, holder: BottomViewHolder) {
                holder.tv.setTextColor(Color.BLUE)
            }

            override fun onCenterTabListener(holder: BaseViewHolder) {

            }

        }
    }

    override fun getNormalViewHolder(): BottomViewHolder {
       return BottomViewHolder(R.layout.view_auto_bottom_tab)
    }

    override fun showContentNormal(truePosition: Int, holder: BottomViewHolder) {

    }

    override fun initNormalTab(holder: BottomViewHolder) {
       holder.tv.setTextColor(Color.GRAY)
    }

}

class BottomViewHolder(layoutId:Int) : BaseViewHolder(layoutId){

    lateinit var tv:TextView

    override fun bindView(itemView: View) {
      tv = itemView.findViewById(R.id.tv)
    }

}