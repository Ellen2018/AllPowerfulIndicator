package com.ellen.allpowerfulindicator.bottomnavigation

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
import com.ellen.indicator.TabTraverse
import com.google.android.material.tabs.TabLayout

class BottomNavigationBarActivity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_bar)

        viewPager2 = findViewById(R.id.view_pager2)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }

        }

        allPowerIndicator.itemTab = BottomNavigationBarItem()

        allPowerIndicator.tabTraverse = object : TabTraverse{
            override fun settingTab(tab: TabLayout.Tab, position: Int, itemView: View) {
                itemView.findViewById<TextView>(R.id.tv_title).text = "item${position}"
            }
        }

        allPowerIndicator.bindViewPager2(viewPager2)

    }

}