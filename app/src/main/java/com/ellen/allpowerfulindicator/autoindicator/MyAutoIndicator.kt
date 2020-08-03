package com.ellen.allpowerfulindicator.autoindicator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseItemTabIndicator
import com.ellen.indicator.TabSelectListener

class MyAutoIndicator : BaseItemTabIndicator(){

    init {
        itemLayout = R.layout.item_my_auto_indicator
        tabSelectListener = object : TabSelectListener{
            override fun onTabReselected(position: Int, itemView: View) {

            }

            override fun onTabUnselected(position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv).visibility = View.VISIBLE
                itemView.findViewById<ImageView>(R.id.iv).visibility = View.GONE
            }

            override fun onTabSelected(position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv).visibility = View.GONE
                itemView.findViewById<ImageView>(R.id.iv).visibility = View.VISIBLE
            }
        }
    }

}