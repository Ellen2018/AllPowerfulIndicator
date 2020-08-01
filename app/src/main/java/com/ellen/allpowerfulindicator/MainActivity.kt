package com.ellen.allpowerfulindicator

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.RectangleGuideIndicator
import com.ellen.indicator.RoundGuideIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.view_pager)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TestFragment()
            }

            override fun getCount(): Int {
                return 8
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return "$position"
            }
        }
        allPowerIndicator.itemTab = RectangleGuideIndicator(Color.BLUE,Color.GREEN)
        allPowerIndicator.bindViewPager(viewPager)
        allPowerIndicator.select(0)
    }
}