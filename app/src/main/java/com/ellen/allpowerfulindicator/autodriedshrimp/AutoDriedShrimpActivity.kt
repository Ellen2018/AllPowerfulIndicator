package com.ellen.allpowerfulindicator.autodriedshrimp

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
import com.ellen.indicator.horizontal.HorizontalIndicator
import com.ellen.indicator.horizontal.expand.bottombar.*
import kotlinx.android.synthetic.main.view_auto_center.view.*

/**
 * 仿虾米音乐底部导航栏效果
 */
class AutoDriedShrimpActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var allPowerIndicator: HorizontalIndicator
    private lateinit var adapter: DefaultBottomCenterBarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dried_shrimp)

        viewPager = findViewById(R.id.view_pager)
        allPowerIndicator = findViewById(R.id.all_power_indicator)

        findViewById<Button>(R.id.bt_color1).setOnClickListener {
            adapter.selectedColor = Color.parseColor("#1d88b4")
            adapter.unSelectedColor = Color.GRAY
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_color2).setOnClickListener {
            adapter.selectedColor = Color.parseColor("#0093b7")
            adapter.unSelectedColor = Color.GRAY
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_color3).setOnClickListener {
            adapter.selectedColor = Color.parseColor("#aadddd")
            adapter.unSelectedColor = Color.GRAY
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.bt_color4).setOnClickListener {
            adapter.selectedColor = Color.parseColor("#a0ce6e")
            adapter.unSelectedColor = Color.GRAY
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

        adapter = DefaultBottomCenterBarAdapter()
        //数据填塞
        for(position in 0 until 4){
            when(position){
                0-> adapter.defaultTabs.add(DefaultTab("新闻", R.drawable.ic_news, true))
                1-> adapter.defaultTabs.add(DefaultTab("视频", R.drawable.ic_video, true))
                2-> adapter.defaultTabs.add(DefaultTab("消息", R.drawable.ic_message, true))
                3-> adapter.defaultTabs.add(DefaultTab("我", R.drawable.ic_me, true))
            }
        }
        //是否包含中间控件
        adapter.isContainsCenter = true

        //自定义中间Tab
        adapter.autoCenterViewHolder = MyCenterHorizontalIndicatorViewHolder(R.layout.view_auto_center,"哈哈哈")

        //取消动画效果
        //adapter.animResource = null

        //设置动画效果
        //adapter.animResource = R.anim.anim_tm

        //设置选择和未选中的颜色
        adapter.selectedColor = Color.RED
        adapter.unSelectedColor = Color.GRAY

        //设置消息圆点颜色
        adapter.roundMessageColor = Color.RED

        //设置监听
        adapter.onTabSelectListener = object : BaseBottomCenterBarAdapter.OnTabSelectListener<CenterHorizontalIndicatorViewHolder,NormalHorizontalIndicatorViewHolder> {

            override fun onCenterTabListener(holder: CenterHorizontalIndicatorViewHolder) {
                (holder as MyCenterHorizontalIndicatorViewHolder).string = "呵呵呵"
                Toast.makeText(this@AutoDriedShrimpActivity, "点击了中间", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }

            override fun onTabReselected(position: Int, holder: NormalHorizontalIndicatorViewHolder) {
                Toast.makeText(this@AutoDriedShrimpActivity,"重选$position",Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(position: Int, holder: NormalHorizontalIndicatorViewHolder) {
                Toast.makeText(this@AutoDriedShrimpActivity,"未选$position",Toast.LENGTH_SHORT).show()
            }

            override fun onTabSelected(position: Int, holder: NormalHorizontalIndicatorViewHolder) {
                Toast.makeText(this@AutoDriedShrimpActivity,"选择$position",Toast.LENGTH_SHORT).show()
            }
        }

        allPowerIndicator.bindViewPager(adapter,viewPager)
    }

    class MyCenterHorizontalIndicatorViewHolder(layoutId:Int, var string: String) : CenterHorizontalIndicatorViewHolder(layoutId){

        override fun notifyDataSetChanged() {
            itemView?.tv?.text = string
        }
    }

}