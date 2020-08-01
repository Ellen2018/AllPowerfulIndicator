package com.ellen.base.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 没有任何数据类型的item,在代码中动态添加item
 * 修改为ViewHolder的方式
 */
class BaseNullAdapter(var mContext: Context) :
    RecyclerView.Adapter<BaseNullAdapter.PositionViewHolder>() {

    private var typeMap: SortedMap<Int, Int>? = null
    private var viewMap: MutableMap<Int, View>? = null
    private var adjustList:MutableList<Int>? = null
    private lateinit var recyclerView:RecyclerView


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun addView(position: Int, view: View) {
        if (typeMap == null) {
            typeMap = TreeMap()
        }
        typeMap!![position] = position
        if(viewMap == null){
            viewMap = HashMap()
        }
        adjust()
        viewMap!![position] = view
        notifyDataSetChanged()
    }

    private fun adjust(){
        adjustList = ArrayList()
        for((key,value) in typeMap!!){
            adjustList!!.add(value)
        }
    }

    /**
     * 移除
     */
    fun removeView(view:View){
        var typeValue = -1
        for((key,value) in viewMap!!){
            if(view == value){
                typeValue = key
            }
        }
        if(typeValue >= 0) {
            removePosition(typeValue)
        }
    }

    /**
     * 清空
     */
    fun clear(){
        adjustList!!.clear()
        viewMap!!.clear()
        typeMap!!.clear()
        notifyDataSetChanged()
    }

    /**
     * 翻转
     */
    fun upsideDown(){
        for(index in 0 until (adjustList!!.size-1)/2){
           val valueCopy = adjustList!![index]
            val index2 = adjustList!!.size-index-1
            adjustList!![index] = adjustList!![index2]
            adjustList!![index2] = valueCopy
        }
        notifyDataSetChanged()
    }

    fun  removePosition(position: Int){
        var removePosition = -1
        for((index,value) in adjustList!!.withIndex()) {
           if(value == position){
               removePosition = index
               break
           }
        }
        if(removePosition >= 0) {
            adjustList!!.removeAt(removePosition)
            typeMap!!.remove(position)
            viewMap!!.remove(position)
            notifyDataSetChanged()
        }
    }

    /**
     * 交换
     */
    fun exChange(index1:Int,index2:Int){
        var i1:Int = -1
        var i2:Int = -1
        for((index,value) in adjustList!!.withIndex()){
            if(index1 == value){
                i1 = index
            }
            if(index2 == value){
                i2 = index
            }
        }
        if(i1 >= 0 && i2 >= 0){
            val valueCopy = adjustList!![i1]
            adjustList!![i1] = adjustList!![i2]
            adjustList!![i2] = valueCopy
        }
        notifyDataSetChanged()
    }

    fun addViewByLayoutId(position: Int,id:Int):View{
        val view = LayoutInflater.from(mContext).inflate(id,null)
        addView(position,view)
        return view
    }


    override fun getItemViewType(position: Int): Int {
        val p = adjustList!![position]
        val typeValue = typeMap!![p]
        return typeValue!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view = viewMap!![viewType]
        return PositionViewHolder(view!!, viewType)
    }

    override fun getItemCount(): Int {
        return adjustList!!.size
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {

    }

    class PositionViewHolder(itemView: View, var itemType: Int) : BaseViewHolder(itemView) {

    }
}