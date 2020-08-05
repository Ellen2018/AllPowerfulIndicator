package com.ellen.allpowerfulindicator.driedshrimp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ellen.allpowerfulindicator.R
import com.ellen.allpowerfulindicator.TestFragment
import com.ellen.indicator.AllPowerIndicator
import com.ellen.indicator.expand.bar.*

/**
 * 仿虾米音乐底部导航栏效果
 */
class DriedShrimpActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var allPowerIndicator: AllPowerIndicator
    private lateinit var adapter: DefaultBottomCenterBarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dried_shrimp)

        viewPager = findViewById(R.id.view_pager)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        findViewById<Button>(R.id.bt_color1).setOnClickListener {
            adapter.selectedColor = Color.BLACK
            adapter.unSelectedColor = Color.RED
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_color2).setOnClickListener {
            adapter.selectedColor = Color.BLUE
            adapter.unSelectedColor = Color.GREEN
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_round_color1).setOnClickListener {
            adapter.roundMessageColor = Color.BLUE
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_round_color2).setOnClickListener {
            adapter.roundMessageColor = Color.GREEN
            adapter.notifyDataSetChanged()
        }

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TestFragment()
            }

            override fun getCount(): Int {
                return 4
            }


        }

        //数据填塞
        val defaultTabs: MutableList<DefaultTab> = ArrayList()
        defaultTabs.add(0, DefaultTab("新闻", R.drawable.ic_news, true))
        defaultTabs.add(1, DefaultTab("视频", R.drawable.ic_video, true))
        defaultTabs.add(2, DefaultTab("消息", R.drawable.ic_message, true))
        defaultTabs.add(3, DefaultTab("我", R.drawable.ic_me, true))

        adapter = DefaultBottomCenterBarAdapter(defaultTabs, R.drawable.ic_center)

        adapter.isContainsCenter = true

        //取消动画效果
        //adapter.animResource = null

        //设置动画效果
        //adapter.animResource = R.anim.xxx

        //设置选择和未选中的颜色
        adapter.selectedColor = Color.RED
        adapter.unSelectedColor = Color.GRAY

        //设置消息圆点颜色
        adapter.roundMessageColor = Color.BLACK

        //绑定ViewPager
        adapter.bindViewPager(viewPager)

        //设置监听
        adapter.onDefaultBottomTabSelectListener = object : OnDefaultBottomTabSelectListener {

            private var position = -1

            override fun onCenterTabListener(holder: CenterViewHolder) {
               Toast.makeText(this@DriedShrimpActivity,"点击了中间",Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(position: Int, holder: NormalViewHolder) {
                Toast.makeText(this@DriedShrimpActivity,"重选$position",Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(position: Int, holder: NormalViewHolder) {
                Toast.makeText(this@DriedShrimpActivity,"未选$position",Toast.LENGTH_SHORT).show()
            }

            override fun onTabSelected(position: Int, holder: NormalViewHolder) {
                Toast.makeText(this@DriedShrimpActivity,"选择$position",Toast.LENGTH_SHORT).show()
            }
        }

        allPowerIndicator.setAdapter(adapter)
    }

}