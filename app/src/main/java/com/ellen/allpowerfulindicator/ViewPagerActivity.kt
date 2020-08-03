package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.RoundGuideIndicator

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

        //设置指示器为圆点(选中：蓝色，未选中:灰色)
        allPowerIndicator.itemTab = RoundGuideIndicator(Color.BLUE,Color.GRAY)

        //绑定指示器到ViewPager
        allPowerIndicator.bindViewPager(viewPager)

    }

}