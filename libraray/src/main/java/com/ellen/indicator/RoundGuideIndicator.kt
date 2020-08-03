package com.ellen.indicator

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import com.ellen.libraray.R

class RoundGuideIndicator(var selectColor: Int, var unSelectColor: Int) : ItemTab() {

    init {
        itemLayout = R.layout.item_tab_layout_round_view
        itemSpacing = 0
        itemMode = Mode.MODE_FIXED
        tabRippleColor = Color.TRANSPARENT
        tabSelectListener = object : TabSelectListener {
            override fun onTabReselected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = selectColor
                itemView.findViewById<View>(R.id.view).background = drawable
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = unSelectColor
                itemView.findViewById<View>(R.id.view).background = drawable
            }

            override fun onTabSelected(position: Int, itemView: View) {
                val drawable = ShapeDrawable(OvalShape())
                drawable.paint.color = selectColor
                itemView.findViewById<View>(R.id.view).background = drawable
            }
        }
    }

}