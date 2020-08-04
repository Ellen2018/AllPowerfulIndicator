package com.ellen.allpowerfulindicator.driedshrimp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseAdapter
import com.ellen.indicator.BaseViewHolder
import com.google.android.material.tabs.TabLayout

class DriedShrimpAdapter : BaseAdapter<BaseViewHolder>(){

    var onTabSelectListener:OnTabSelectListener? = null

    override fun getItemType(position: Int): Int {
        return if(position == 2){
            DriedShrimpType.Center.type
        }else{
            DriedShrimpType.Normal.type
        }
    }

    override fun getViewHolder(viewType: Int): BaseViewHolder {
        return if(viewType == DriedShrimpType.Normal.type){
            NormalViewHolder(R.layout.view_left_right)
        }else{
            CenterViewHolder(R.layout.view_center)
        }
    }

    override fun getItemSize(): Int {
       return 5
    }

    override fun showContent(holder: BaseViewHolder) {

    }

    override fun settingTabLayout(tabLayout: TabLayout) {
        super.settingTabLayout(tabLayout)
        tabLayout.setSelectedTabIndicator(null)
    }

    override fun onTabReselected(holder: BaseViewHolder) {
       onTabSelectListener?.onTabReselected(holder)
    }

    override fun onTabUnselected(holder: BaseViewHolder) {
        onTabSelectListener?.onTabUnselected(holder)
    }

    override fun onTabSelected(holder: BaseViewHolder) {
        onTabSelectListener?.onTabSelected(holder)
    }

}

class CenterViewHolder(itemLayoutId:Int) : BaseViewHolder(itemLayoutId){

    lateinit var iv:ImageView

    override fun bindView(itemView: View) {
       iv = itemView.findViewById(R.id.iv)
    }
}

class NormalViewHolder(itemLayoutId:Int) : BaseViewHolder(itemLayoutId){

    lateinit var tv:TextView

    override fun bindView(itemView: View) {
        tv = itemView.findViewById(R.id.tv)
    }
}

enum class DriedShrimpType(var type: Int){
    Center(1),
    Normal(2)
}

interface OnTabSelectListener{
    fun onTabReselected(holder: BaseViewHolder)
    fun onTabUnselected(holder: BaseViewHolder)
    fun onTabSelected(holder: BaseViewHolder)
}