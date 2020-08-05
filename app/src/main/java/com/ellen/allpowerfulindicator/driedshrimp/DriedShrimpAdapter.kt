package com.ellen.allpowerfulindicator.driedshrimp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseBottomBarAdapter
import com.ellen.indicator.BaseViewHolder

class DriedShrimpAdapter : BaseBottomBarAdapter<CenterViewHolder, NormalViewHolder>() {


    override fun getItemCount(): Int {
        return 8
    }

    override fun getCenterViewHolder(): CenterViewHolder? {
        return return CenterViewHolder(R.layout.view_center)
    }

    override fun getNormalViewHolder(): NormalViewHolder {
        return NormalViewHolder(R.layout.view_left_right)
    }

    override fun showContentCenter(holder: CenterViewHolder) {

    }

    override fun showContentNormal(truePosition: Int, holder: NormalViewHolder) {
        holder.tv.text = truePosition.toString()
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