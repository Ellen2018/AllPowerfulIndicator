package com.ellen.allpowerfulindicator.fragment

import android.os.Bundle
import android.widget.TextView
import com.ellen.allpowerfulindicator.R

class TestFragment : BaseFragment(){
    override fun getLayoutId(): Int {
       return R.layout.fragment_test
    }

    companion object{
        fun getTestFragment(string:String):TestFragment{
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putString("string",string)
            testFragment.arguments = bundle
            return testFragment
        }
    }

    override fun initView() {
        val tv = findViewById<TextView>(R.id.tv)
        tv.text = arguments?.getString("string")
    }

    override fun initData() {

    }
}