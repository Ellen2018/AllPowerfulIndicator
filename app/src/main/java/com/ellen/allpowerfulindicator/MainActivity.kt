package com.ellen.allpowerfulindicator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ellen.allpowerfulindicator.verticalindicator.VerticalViewPager2Activity

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
        findViewById<Button>(R.id.bt_top_wy_bar).setOnClickListener(this)
        findViewById<Button>(R.id.bt_v_indicator).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_v_indicator->
                intent = Intent(this@MainActivity,VerticalViewPager2Activity::class.java)
        }
        startActivity(intent)
    }
}