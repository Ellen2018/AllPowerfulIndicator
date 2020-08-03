package com.ellen.indicator

import android.view.View
import com.ellen.libraray.R

class RectangleGuideItemTabIndicator(var selectColor: Int, var unSelectColor: Int) : BaseItemTabIndicator() {

    init {
        itemLayout = R.layout.item_tab_layout_rectangle_view
        tabSelectListener = object : TabSelectListener {
            override fun onTabReselected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundColor(selectColor)
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundColor(unSelectColor)
            }

            override fun onTabSelected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundColor(selectColor)
            }
        }
    }

}