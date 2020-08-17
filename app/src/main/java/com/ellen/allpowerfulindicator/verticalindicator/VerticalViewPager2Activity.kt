package com.ellen.allpowerfulindicator.verticalindicator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.vertical.AllPowerfulIndicator
import com.ellen.indicator.vertical.OnTabClickListener

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
        allPowerIndicator.mode = AllPowerfulIndicator.Mode.FIXED

        viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager2.adapter = object : FragmentStateAdapter(this){

            override fun getItemCount(): Int {
                return 5
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
                return 60
            }
        }

        val adapter = VerticalIndicatorAdapter()
        adapter.onTabClickListener =
            object : OnTabClickListener<VerticalIndicatorAdapter.IndicatorViewHolder> {
                override fun onTabSelectedClick(
                    position: Int,
                    holder: VerticalIndicatorAdapter.IndicatorViewHolder
                ) {
                    Toast.makeText(holder.itemView.context, "选择{$position}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onTabUnSelectedClick(
                    position: Int,
                    holder: VerticalIndicatorAdapter.IndicatorViewHolder
                ) {
                    Toast.makeText(holder.itemView.context, "未选择{$position}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onTabReSelectedClick(
                    position: Int,
                    holder: VerticalIndicatorAdapter.IndicatorViewHolder
                ) {
                    Toast.makeText(holder.itemView.context, "重选{$position}", Toast.LENGTH_SHORT)
                        .show()
                    adapter.color = Color.YELLOW
                    adapter.notifyDataSetChanged()
                }

                override fun onNoStatusTabClick(
                    position: Int,
                    holder: VerticalIndicatorAdapter.IndicatorViewHolder
                ) {
                    Toast.makeText(holder.itemView.context, "无状态点击：{$position}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        allPowerIndicator.bindViewPager(adapter,viewPager)

        allPowerIndicator.currentItem = 3

    }

}