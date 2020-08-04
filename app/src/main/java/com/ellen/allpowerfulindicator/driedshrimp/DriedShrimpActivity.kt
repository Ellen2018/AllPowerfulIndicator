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
        adapter.onTabSelectListener = object : OnTabSelectListener {

            private var ago = -1

            override fun onTabReselected(holder: BaseViewHolder) {

            }

            override fun onTabUnselected(holder: BaseViewHolder) {
                if (holder is CenterViewHolder) {

                } else if (holder is NormalViewHolder) {
                    holder.tv.setTextColor(Color.GRAY)
                }
               ago = holder.position
            }

            override fun onTabSelected(holder: BaseViewHolder) {
                if (holder is CenterViewHolder) {
                    Toast.makeText(this@DriedShrimpActivity, "哈哈哈", Toast.LENGTH_SHORT).show()
                    allPowerIndicator.selectTab(allPowerIndicator.getTabAt(ago))
                } else if (holder is NormalViewHolder) {
                    holder.tv.setTextColor(Color.BLUE)
                    if(holder.position >= 2){
                        viewPager2.currentItem = holder.position -1
                    }else{
                        viewPager2.currentItem = holder.position
                    }
                }
            }
        }
        allPowerIndicator.setAdapter(adapter)
    }

}