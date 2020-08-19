package com.ellen.allpowerfulindicator.verticalindicator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.test.AllPowerfulIndicator
import com.ellen.indicator.test.OnTabClickListener
import com.ellen.indicator.test.view.BaseIndicatorViewHolder

class VerticalViewPager2Activity : AppCompatActivity(){

    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager: ViewPager
    private lateinit var allPowerIndicator: AllPowerfulIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertical_view_pager2)

        viewPager2 = findViewById(R.id.view_pager2)
        viewPager = findViewById(R.id.view_pager)
        allPowerIndicator = findViewById(R.id.vertical_indicator)
        allPowerIndicator.orientation = AllPowerfulIndicator.Orientation.HORIZONTAL
        allPowerIndicator.mode = AllPowerfulIndicator.Mode.SCROLL
        allPowerIndicator.clickGravity = AllPowerfulIndicator.ClickGravity.CENTER

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 30
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }
        }

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
               return TestFragment()
            }

            override fun getCount(): Int {
                return 30
            }
        }

        val adapter = VerticalIndicatorAdapter()

        adapter.addOnTabClickListener(object : OnTabClickListener<VerticalIndicatorAdapter.IndicatorViewHolder>{
            override fun onTabSelectedClick(
                position: Int,
                holder: VerticalIndicatorAdapter.IndicatorViewHolder
            ) {
                Toast.makeText(this@VerticalViewPager2Activity,"选中${position}",Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnSelectedClick(
                position: Int,
                holder: VerticalIndicatorAdapter.IndicatorViewHolder
            ) {
                Toast.makeText(this@VerticalViewPager2Activity,"未选中${position}",Toast.LENGTH_SHORT).show()
            }

            override fun onTabReSelectedClick(
                position: Int,
                holder: VerticalIndicatorAdapter.IndicatorViewHolder
            ) {
                Toast.makeText(this@VerticalViewPager2Activity,"重选${position}",Toast.LENGTH_SHORT).show()
            }

            override fun onNoStatusTabClick(position: Int, holder: BaseIndicatorViewHolder) {
               Toast.makeText(this@VerticalViewPager2Activity,"无状态点击${position}",Toast.LENGTH_SHORT).show()
            }
        })

        allPowerIndicator.bindViewPager2(adapter,viewPager2,0)
    }

}