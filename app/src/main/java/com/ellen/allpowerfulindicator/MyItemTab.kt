package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import com.ellen.indicator.ItemTab
import com.ellen.indicator.Mode
import com.ellen.indicator.TabSelectListener

class MyItemTab : ItemTab(){

    init {
        itemLayout = R.layout.item_tab_layout_round_my_view
        itemMode = Mode.MODE_SCROLLABLE
        tabSelectListener = object : TabSelectListener{
            override fun onTabReselected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = Color.RED
                itemView.findViewById<View>(com.ellen.libraray.R.id.view).background = drawable
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = Color.YELLOW
                itemView.findViewById<View>(com.ellen.libraray.R.id.view).background = drawable
            }

            override fun onTabSelected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = Color.RED
                itemView.findViewById<View>(com.ellen.libraray.R.id.view).background = drawable
            }
        }

    }

}