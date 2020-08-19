package com.ellen.allpowerfulindicator.verticalindicator

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.fragment.TestFragment
import com.ellen.indicator.AllPowerfulIndicator
import com.ellen.indicator.OnTabClickListener
import com.ellen.indicator.expand.topbar.TopTextViewBarAdapter
import com.ellen.indicator.view.BaseIndicatorViewHolder

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
        allPowerIndicator.orientation = AllPowerfulIndicator.Orientation.VERTICAL
        allPowerIndicator.clickGravity = AllPowerfulIndicator.ClickGravity.CENTER
        allPowerIndicator.mode = AllPowerfulIndicator.Mode.SCROLL

        val count = 30
        viewPager.visibility = View.GONE
        viewPager2.visibility = View.VISIBLE

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return count
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment.getTestFragment("$position")
            }
        }

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
               return TestFragment.getTestFragment("$position")
            }

            override fun getCount(): Int {
                return count
            }
        }

        val adapter = TopTextViewBarAdapter()
        adapter.addOnTabClickListener(object : OnTabClickListener<TopTextViewBarAdapter.TopTextViewHolder>{
            override fun onTabSelectedClick(
                position: Int,
                holder: TopTextViewBarAdapter.TopTextViewHolder
            ) {
               Log.e("Ellen2018","当前的位置：${allPowerIndicator.currentItem}")
            }

            override fun onTabUnSelectedClick(
                position: Int,
                holder: TopTextViewBarAdapter.TopTextViewHolder
            ) {

            }

            override fun onTabReSelectedClick(
                position: Int,
                holder: TopTextViewBarAdapter.TopTextViewHolder
            ) {
            }
        })
        allPowerIndicator.bindViewPager2(adapter,viewPager2)
    }

}