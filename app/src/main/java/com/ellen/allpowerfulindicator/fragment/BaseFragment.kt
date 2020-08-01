package com.ellen.allpowerfulindicator.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * 1.懒加载
 * 2.软件盘
 */
abstract class BaseFragment : Fragment(){

    protected lateinit var mContentView:View

    //懒加载标记值
    private var isFirstShow = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContentView = inflater.inflate(getLayoutId(),container,false)
        initView()
        initData()
        return mContentView
    }

    fun <T : View?> findViewById(id:Int):T{
        return mContentView.findViewById<T>(id)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden){
            if(!isFirstShow){
                lazyLoading()
                isFirstShow = true
            }
        }
    }

    protected abstract fun getLayoutId():Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected fun lazyLoading(){}


}