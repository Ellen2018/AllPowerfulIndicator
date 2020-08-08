package com.ellen.allpowerfulindicator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ellen.allpowerfulindicator.atuobottombar.AutoBottomBarActivity
import com.ellen.allpowerfulindicator.autodriedshrimp.AutoDriedShrimpActivity
import com.ellen.allpowerfulindicator.autoindicator.AutoIndicatorActivity
import com.ellen.allpowerfulindicator.bottomnavigation.BottomNavigationBarActivity
import com.ellen.allpowerfulindicator.driedshrimp.DriedShrimpActivity
import com.ellen.allpowerfulindicator.free.FreeIndicatorActivity
import com.ellen.allpowerfulindicator.topbar.AutoTopBarActivity
import com.ellen.allpowerfulindicator.topbar.OriginalTopBarActivity

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.bt_view_pager).setOnClickListener(this)
        findViewById<Button>(R.id.bt_view_pager2).setOnClickListener(this)
        findViewById<Button>(R.id.bt_bottom_navigation_bar).setOnClickListener(this)
        findViewById<Button>(R.id.bt_auto_indicator).setOnClickListener(this)
        findViewById<Button>(R.id.bt_xm).setOnClickListener(this)
        findViewById<Button>(R.id.bt_xm_auto_center).setOnClickListener(this)
        findViewById<Button>(R.id.bt_auto_bottom_bar).setOnClickListener(this)
        findViewById<Button>(R.id.bt_free_indicator).setOnClickListener(this)
        findViewById<Button>(R.id.bt_top_o_bar).setOnClickListener(this)
        findViewById<Button>(R.id.bt_top_auto_bar).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_view_pager->
                 intent = Intent(this@MainActivity,ViewPagerActivity::class.java)
            R.id.bt_view_pager2->
                intent = Intent(this@MainActivity,ViewPager2Activity::class.java)
            R.id.bt_bottom_navigation_bar->
                intent = Intent(this@MainActivity,BottomNavigationBarActivity::class.java)
            R.id.bt_auto_indicator->
                intent = Intent(this@MainActivity,AutoIndicatorActivity::class.java)
            R.id.bt_xm->
                intent = Intent(this@MainActivity,DriedShrimpActivity::class.java)
            R.id.bt_xm_auto_center->
                intent = Intent(this@MainActivity,AutoDriedShrimpActivity::class.java)
            R.id.bt_auto_bottom_bar->
                intent = Intent(this@MainActivity,AutoBottomBarActivity::class.java)
            R.id.bt_free_indicator->
                intent = Intent(this@MainActivity,FreeIndicatorActivity::class.java)
            R.id.bt_top_o_bar->
                intent = Intent(this@MainActivity,OriginalTopBarActivity::class.java)
            R.id.bt_top_auto_bar->
                intent = Intent(this@MainActivity,AutoTopBarActivity::class.java)
        }
        startActivity(intent)
    }
}