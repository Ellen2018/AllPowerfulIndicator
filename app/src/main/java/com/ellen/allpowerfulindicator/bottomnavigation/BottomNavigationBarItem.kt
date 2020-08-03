package com.ellen.allpowerfulindicator.bottomnavigation

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.ItemTab
import com.ellen.indicator.Mode
import com.ellen.indicator.TabSelectListener

class BottomNavigationBarItem : ItemTab(){

    init {
        itemLayout = R.layout.item_bottom_navigation_bar
        itemMode = Mode.MODE_FIXED
        tabSelectListener = object : TabSelectListener {
            override fun onTabReselected(position: Int, itemView: View) {
                //重选
                //itemView.findViewById<TextView>(R.id.tv_title).setTextColor(Color.BLUE)
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                //未选中
                itemView.findViewById<TextView>(R.id.tv_title).setTextColor(Color.GRAY)
            }

            override fun onTabSelected(position: Int, itemView: View) {
                //选中
                itemView.findViewById<TextView>(R.id.tv_title).setTextColor(Color.BLUE)
            }
        }
    }

}