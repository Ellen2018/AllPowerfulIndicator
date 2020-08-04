package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.BaseViewHolder
import com.ellen.indicator.RectangleIndicatorAdapter
import com.ellen.indicator.RoundIndicatorAdapter

class ViewPagerActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewPager = findViewById(R.id.view_pager)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
               return TestFragment()
            }

            override fun getCount(): Int {
               return 4
            }
        }

        allPowerIndicator.setAdapter(RoundIndicatorAdapter(viewPager,Color.BLUE,Color.GRAY))
    }

}
