package com.ellen.indicator.vertical

import android.view.ViewGroup

abstract class Adapter<T : BaseIndicatorViewHolder>{

    var onTabClickListener:OnTabClickListener<T>? = null
    protected lateinit var allPowerfulIndicator:AllPowerfulIndicator

    fun bindAllPowerfulIndicator(allPowerfulIndicator: AllPowerfulIndicator){
        this.allPowerfulIndicator = allPowerfulIndicator
    }

    /**
     * item类型
     */
    abstract fun getItemType(position:Int):Int
    abstract fun getViewHolder(parent:ViewGroup,viewType:Int): T
    abstract fun getItemCount():Int
    abstract fun showContent(position:Int,holder:T)
    abstract fun selectedStatus(position:Int,holder: T)
    abstract fun unSelectedStatus(position:Int,holder: T)
    abstract fun reSelectedStatus(
        position:Int,
        holder: T
    )

    /**
     * 映射为外部使用的position
     */
    open fun outStatusPosition(position: Int):Int{
        return position
    }

    /**
     * 映射为内部使用的position
     */
    open fun inStatusPosition(position: Int):Int{
        return position
    }

    open fun notifyDataSetChanged(){
        allPowerfulIndicator.adapter?.notifyDataSetChanged()
    }

}

interface OnTabClickListener<T : BaseIndicatorViewHolder>{
    fun onTabSelectedClick(position: Int,holder: T)
    fun onTabUnSelectedClick(position: Int,holder: T)
    fun onTabReSelectedClick(position: Int,holder: T)
    fun onNoStatusTabClick(position: Int,holder: T){

    }
}