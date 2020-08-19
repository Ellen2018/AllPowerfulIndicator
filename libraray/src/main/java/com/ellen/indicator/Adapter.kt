package com.ellen.indicator

import android.view.ViewGroup
import com.ellen.indicator.view.BaseIndicatorViewHolder

abstract class Adapter<T : BaseIndicatorViewHolder>{

    internal var onTabClickListenerList:MutableList<OnTabClickListener<T>> = ArrayList()
    internal lateinit var allPowerfulIndicator: AllPowerfulIndicator
    internal var trueCount = -1
    internal var statusManager = StatusManager()

    open fun onAttach(allPowerfulIndicator: AllPowerfulIndicator){
        this.allPowerfulIndicator = allPowerfulIndicator
    }

    fun addOnTabClickListener(onTabClickListener: OnTabClickListener<T>){
        onTabClickListenerList.add(onTabClickListener)
    }

    fun removeOnTabClickListener(onTabClickListener: OnTabClickListener<T>){
        onTabClickListenerList.remove(onTabClickListener)
    }

    /**
     * item类型
     */
    abstract fun getItemType(position:Int):Int
    abstract fun getViewHolder(parent:ViewGroup,viewType:Int): T

    /**
     * 获取有状态的item个数
     */
    open fun getStatusItemCount():Int{
        return 0
    }

    /**
     * 获取无状态的item个数
     */
    open fun getNoStatusItemCount():Int{
        return 0
    }

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

    /**
     * 无状态position转化
     */
    open fun outNoStatusPosition(position: Int):Int{
        return position
    }

    open fun notifyDataSetChanged(){
        allPowerfulIndicator.notifyDataSetChanged()
    }
}

internal class StatusManager{
    var selectPosition = 0
}


interface OnTabClickListener<T : BaseIndicatorViewHolder>{
    fun onTabSelectedClick(position: Int,holder: T)
    fun onTabUnSelectedClick(position: Int,holder: T)
    fun onTabReSelectedClick(position: Int,holder: T)
    fun onNoStatusTabClick(position: Int,holder: BaseIndicatorViewHolder){

    }
}