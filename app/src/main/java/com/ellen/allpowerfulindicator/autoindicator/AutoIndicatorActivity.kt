package com.ellen.allpowerfulindicator.autoindicator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.horizontal.HorizontalIndicator

class AutoIndicatorActivity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var allPowerIndicator: HorizontalIndicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

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

        val strList:MutableList<String> = ArrayList()
        strList.add("a")
        strList.add("b")
        strList.add("c")
        strList.add("d")
        allPowerIndicator.bindViewPager2(AutoIndicatorAdapter(strList),viewPager2)
    }

}