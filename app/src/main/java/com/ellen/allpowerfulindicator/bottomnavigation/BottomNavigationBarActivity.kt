package com.ellen.allpowerfulindicator.bottomnavigation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.expand.bar.DefaultBottomCenterBarAdapter
import com.ellen.indicator.expand.bar.DefaultTab

class BottomNavigationBarActivity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_bar)

        viewPager2 = findViewById(R.id.view_pager2)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }

        }

        val adapter = DefaultBottomCenterBarAdapter()
        adapter.defaultTabs.add(0, DefaultTab("新闻", R.drawable.ic_news, true))
        adapter.defaultTabs.add(1, DefaultTab("视频", R.drawable.ic_video, true))
        adapter.defaultTabs.add(2, DefaultTab("消息", R.drawable.ic_message, true))
        adapter.defaultTabs.add(3, DefaultTab("我", R.drawable.ic_me, true))
        adapter.centerImageResource = R.drawable.ic_center
        allPowerIndicator.bindViewPager2(adapter,viewPager2)
    }

}