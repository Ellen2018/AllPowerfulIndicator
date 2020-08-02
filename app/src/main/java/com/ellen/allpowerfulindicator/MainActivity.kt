package com.ellen.allpowerfulindicator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.TabTraverse

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
               return 4
            }

            override fun createFragment(position: Int): Fragment {
               return TestFragment()
            }

        }
        allPowerIndicator.tabTraverse = object : TabTraverse{
            override fun settingTab(position: Int, itemView: View) {
               itemView.findViewById<TextView>(R.id.tv).text = "$position"
            }
        }
        viewPager.isUserInputEnabled = false
        allPowerIndicator.itemTab = MyItemTab()
        allPowerIndicator.bindViewPager2(viewPager)
        allPowerIndicator.select(0)
    }
}