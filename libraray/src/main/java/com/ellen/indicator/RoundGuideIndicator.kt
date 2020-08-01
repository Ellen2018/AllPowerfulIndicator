package com.ellen.indicator

import android.view.View
import com.ellen.libraray.R

class RoundGuideIndicator : ItemTab() {

    init {
        itemLayout = R.layout.item_tab_layout_round_view
        itemSpacing = 0
        itemMode = Mode.MODE_FIXED
        tabSelectListener = object : TabSelectListener {
            override fun onTabReselected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundResource(R.drawable.round_red)
            }

            override fun onTabUnselected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundResource(R.drawable.round_blue)
            }

            override fun onTabSelected(position: Int, itemView: View) {
                itemView.findViewById<View>(R.id.view).setBackgroundResource(R.drawable.round_red)
            }
        }
    }

}