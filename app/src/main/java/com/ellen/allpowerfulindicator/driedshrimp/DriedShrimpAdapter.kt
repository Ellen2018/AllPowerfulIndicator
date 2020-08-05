package com.ellen.allpowerfulindicator.driedshrimp

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.BaseBottomBarAdapter
import com.ellen.indicator.BaseViewHolder

class DriedShrimpAdapter : BaseBottomBarAdapter<CenterViewHolder, NormalViewHolder>() {

    override fun getCenterViewHolder(): CenterViewHolder{
        return CenterViewHolder(R.layout.view_center)
    }

    override fun getNormalViewHolder(): NormalViewHolder {
        return NormalViewHolder(R.layout.item_bottom_navigation_bar)
    }

    override fun showContentCenter(holder: CenterViewHolder) {

    }

    override fun showContentNormal(truePosition: Int, holder: NormalViewHolder) {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = Color.RED
        holder.view.background = drawable

        when (truePosition){
            0-> {
                holder.iv.setImageResource(R.drawable.ic_news)
                holder.tvTitle.text = "新闻"
            }
            1-> {
                holder.iv.setImageResource(R.drawable.ic_video)
                holder.tvTitle.text = "视频"
            }
            2-> {
                holder.iv.setImageResource(R.drawable.ic_message)
                holder.tvTitle.text = "消息"
            }
            3-> {
                holder.iv.setImageResource(R.drawable.ic_me)
                holder.tvTitle.text = "我"
            }
        }
    }

}

class CenterViewHolder(itemLayoutId:Int) : BaseViewHolder(itemLayoutId){

    lateinit var iv:ImageView

    override fun bindView(itemView: View) {
       iv = itemView.findViewById(R.id.iv)
    }
}

class NormalViewHolder(itemLayoutId:Int) : BaseViewHolder(itemLayoutId){

    lateinit var tvTitle: TextView
    lateinit var view: View
    lateinit var iv: ImageView

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        view = itemView.findViewById(R.id.view_round)
        iv = itemView.findViewById(R.id.iv)
    }
}