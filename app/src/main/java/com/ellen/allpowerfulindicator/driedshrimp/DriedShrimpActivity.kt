package com.ellen.allpowerfulindicator.driedshrimp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.BaseViewHolder
import com.ellen.indicator.OnTabSelectListener

/**
 * 仿虾米音乐底部导航栏效果
 */
class DriedShrimpActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var allPowerIndicator: AllPowerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dried_shrimp)

        viewPager2 = findViewById(R.id.view_pager2)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return TestFragment()
            }
        }

        val adapter = DriedShrimpAdapter()
        adapter.onTabSelectListener = object : OnTabSelectListener<CenterViewHolder,NormalViewHolder>{
            override fun onTabReselected(truePosition: Int, holder: NormalViewHolder) {

            }

            override fun onTabUnselected(truePosition: Int, holder: NormalViewHolder) {
                holder.tv.setTextColor(Color.GRAY)
            }

            override fun onTabSelected(truePosition: Int, holder: NormalViewHolder) {
                holder.tv.setTextColor(Color.BLUE)
            }

            override fun onCenterTabListener(holder: CenterViewHolder) {

            }

            override fun bindViewPager2(): ViewPager2? {
                return viewPager2
            }

        }
        allPowerIndicator.setAdapter(adapter)
    }

}