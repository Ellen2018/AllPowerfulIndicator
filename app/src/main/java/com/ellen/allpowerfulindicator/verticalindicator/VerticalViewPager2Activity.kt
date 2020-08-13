package com.ellen.allpowerfulindicator.verticalindicator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.horizontal.HorizontalIndicator
import com.ellen.indicator.vertical.VerticalIndicator

class VerticalViewPager2Activity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var verticalIndicator: VerticalIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_view_pager2)

        viewPager2 = findViewById(R.id.view_pager2)
        verticalIndicator = findViewById(R.id.vertical_indicator)

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }
        }

        verticalIndicator.bindViewPager2(VerticalIndicatorAdapter(),viewPager2)
    }

}