package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.TextView
import com.ellen.indicator.ItemTab
import com.ellen.indicator.Mode
import com.ellen.indicator.TabSelectListener

class MyItemTab : ItemTab(){

    init {
        itemLayout = R.layout.item_tab_layout_round_my_view
        itemMode = Mode.MODE_FIXED
        tabSelectListener = object : TabSelectListener{
            override fun onTabReselected(position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv).setTextColor(Color.RED)
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv).setTextColor(Color.BLUE)
            }

            override fun onTabSelected(position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv).setTextColor(Color.RED)
            }
        }

    }

}