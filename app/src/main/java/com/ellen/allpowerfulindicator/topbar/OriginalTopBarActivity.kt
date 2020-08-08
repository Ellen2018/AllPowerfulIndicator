package com.ellen.allpowerfulindicator.topbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.expand.topbar.OriginalTopBarAdapter
import com.google.android.material.tabs.TabLayout

class OriginalTopBarActivity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_bar)

        viewPager2 = findViewById(R.id.view_pager2)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 12
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }

        }

        val adapter = OriginalTopBarAdapter()

        adapter.traverse(object : OriginalTopBarAdapter.Traverse{
            override fun showContent(tab: TabLayout.Tab) {
                tab.text = "哈哈哈"
            }
        })

        //使用原始的顶部导航栏
        allPowerIndicator.bindViewPager2(adapter,viewPager2)



    }

}