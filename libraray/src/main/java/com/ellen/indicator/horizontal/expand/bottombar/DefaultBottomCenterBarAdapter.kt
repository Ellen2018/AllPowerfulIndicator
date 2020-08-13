package com.ellen.indicator.horizontal.expand.bottombar

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.indicator.horizontal.BaseHorizontalIndicatorViewHolder
import com.ellen.libraray.R

class DefaultBottomCenterBarAdapter : BaseBottomCenterBarAdapter<CenterHorizontalIndicatorViewHolder, NormalHorizontalIndicatorViewHolder>() {

    var defaultTabs: MutableList<DefaultTab> = ArrayList()
    var autoCenterViewHolder: CenterHorizontalIndicatorViewHolder? = null
    var centerImageResource: Int? = null
    var isContainsCenter = false
    var selectedColor: Int = Color.BLUE
    var unSelectedColor = Color.GRAY
    var roundMessageColor = Color.RED

    override fun getCenterViewHolder(): CenterHorizontalIndicatorViewHolder? {
        return if (isContainsCenter) {
            if (autoCenterViewHolder != null) {
                return autoCenterViewHolder
            } else {
                CenterHorizontalIndicatorViewHolder(R.layout.item_view_center)
            }
        } else {
            null
        }
    }

    override fun getNormalViewHolder(): NormalHorizontalIndicatorViewHolder {
        return NormalHorizontalIndicatorViewHolder(R.layout.item_view_normal)
    }

    override fun showContentNormal(truePosition: Int, holder: NormalHorizontalIndicatorViewHolder) {
        holder.tvTitle.text = defaultTabs[truePosition].title
        defaultTabs[truePosition].imageResource.let { holder.iv.setImageResource(it) }
        if (defaultTabs[truePosition].isHaveMessage) {
            holder.view.visibility = View.VISIBLE
            val drawable = ShapeDrawable(OvalShape())
            drawable.paint.color = roundMessageColor
            holder.view.background = drawable
        } else {
            holder.view.visibility = View.GONE
        }
    }

    override fun showContentCenter(holder: CenterHorizontalIndicatorViewHolder) {
        if (autoCenterViewHolder == null) {
            centerImageResource?.let {
                holder.itemView?.findViewById<ImageView>(R.id.iv)?.setImageResource(it)
            }
        } else {
            autoCenterViewHolder?.notifyDataSetChanged()
        }
    }

    override fun initCenterTab(holder: CenterHorizontalIndicatorViewHolder) {

    }

    override fun initNormalTab(holder: NormalHorizontalIndicatorViewHolder) {
        unSelectedStatus(holder)
    }

    override fun selectedStatus(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
        if(holderHorizontalIndicator is NormalHorizontalIndicatorViewHolder) {
            holderHorizontalIndicator.iv.imageTintList = ColorStateList.valueOf(selectedColor)
            holderHorizontalIndicator.tvTitle.setTextColor(selectedColor)
        }
    }

    override fun unSelectedStatus(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {
        if(holderHorizontalIndicator is NormalHorizontalIndicatorViewHolder) {
            holderHorizontalIndicator.iv.imageTintList = ColorStateList.valueOf(unSelectedColor)
            holderHorizontalIndicator.tvTitle.setTextColor(unSelectedColor)
        }else{
            if(autoCenterViewHolder != null){
                autoCenterViewHolder?.notifyDataSetChanged()
            }else{
                val centerViewHolder = holderHorizontalIndicator as CenterHorizontalIndicatorViewHolder
                centerViewHolder.notifyDataSetChanged()
            }
        }
    }

    override fun reSelectedStatus(holderHorizontalIndicator: BaseHorizontalIndicatorViewHolder) {

    }
}

open class CenterHorizontalIndicatorViewHolder(itemLayoutId: Int) : BaseHorizontalIndicatorViewHolder(itemLayoutId) {

    override fun bindView(itemView: View) {

    }

    open fun notifyDataSetChanged() {}
}

class NormalHorizontalIndicatorViewHolder(itemLayoutId: Int) : BaseHorizontalIndicatorViewHolder(itemLayoutId) {


    lateinit var tvTitle: TextView
    lateinit var iv: ImageView
    lateinit var view: View

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        iv = itemView.findViewById(R.id.iv)
        view = itemView.findViewById(R.id.view_round)
    }
}

