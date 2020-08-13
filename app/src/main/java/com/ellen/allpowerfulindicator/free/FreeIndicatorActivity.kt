package com.ellen.allpowerfulindicator.free

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.horizontal.HorizontalIndicator
import com.ellen.indicator.horizontal.expand.indicator.RectangleIndicatorAdapter
import com.google.android.material.tabs.TabLayout

class FreeIndicatorActivity : AppCompatActivity(){

    private lateinit var allPowerIndicator: HorizontalIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        allPowerIndicator = findViewById(R.id.all_power_indicator)

        val adapter = RectangleIndicatorAdapter(Color.YELLOW,Color.BLUE,10)

        //短横线指示器
        allPowerIndicator.bindFree(adapter)

        adapter.selectColor = Color.GREEN
        adapter.notifyDataSetChanged()

        allPowerIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@FreeIndicatorActivity,"${tab?.position}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}