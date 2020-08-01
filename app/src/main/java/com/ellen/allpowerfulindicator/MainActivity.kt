package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.RectangleGuideIndicator
import com.ellen.indicator.RoundGuideIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.view_pager2)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
               return 9
            }

            override fun createFragment(position: Int): Fragment {
               return TestFragment()
            }

        }
        allPowerIndicator.itemTab = RoundGuideIndicator(Color.YELLOW,Color.RED)
        allPowerIndicator.bindViewPager2(viewPager)
        allPowerIndicator.select(0)
    }
}