package com.ellen.indicator.expand.bar

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ellen.indicator.BaseIndicatorViewHolder
import com.ellen.libraray.R

class DefaultBottomCenterBarAdapter : BaseBottomCenterBarAdapter<CenterIndicatorViewHolder, NormalIndicatorViewHolder>() {

    var defaultTabs: MutableList<DefaultTab> = ArrayList()
    var autoCenterViewHolder: CenterIndicatorViewHolder? = null
    var centerImageResource: Int? = null
        set(value) {
            if (field != value) {
                isChange = true
            }
            field = value
        }
    var isContainsCenter = false
    private var isChange = false
    var selectedColor: Int = Color.BLUE
        set(value) {
            if (field != value) {
                isChange = true
            }
            field = value
        }
    var unSelectedColor = Color.GRAY
        set(value) {
            if (field != value) {
                isChange = true
            }
            field = value
        }

    var roundMessageColor = Color.RED

    override fun getCenterViewHolder(): CenterIndicatorViewHolder? {
        return if (isContainsCenter) {
            if (autoCenterViewHolder != null) {
                return autoCenterViewHolder
            } else {
                CenterIndicatorViewHolder(R.layout.item_view_center)
            }
        } else {
            null
        }
    }

    override fun getNormalViewHolder(): NormalIndicatorViewHolder {
        return NormalIndicatorViewHolder(R.layout.item_view_normal)
    }

    override fun showContentNormal(truePosition: Int, holder: NormalIndicatorViewHolder) {
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

    override fun showContentCenter(holder: CenterIndicatorViewHolder) {
        if (autoCenterViewHolder == null) {
            centerImageResource?.let {
                holder.itemView?.findViewById<ImageView>(R.id.iv)?.setImageResource(it)
            }
        } else {
            autoCenterViewHolder?.notifyDataSetChanged()
        }
    }

    override fun initCenterTab(holder: CenterIndicatorViewHolder) {

    }

    override fun initNormalTab(holder: NormalIndicatorViewHolder) {
        unSelectedStatus(holder)
    }

    override fun selectedStatus(holderIndicator: BaseIndicatorViewHolder) {
        if(holderIndicator is NormalIndicatorViewHolder) {
            holderIndicator.iv.imageTintList = ColorStateList.valueOf(selectedColor)
            holderIndicator.tvTitle.setTextColor(selectedColor)
        }
    }

    override fun unSelectedStatus(holderIndicator: BaseIndicatorViewHolder) {
        if(holderIndicator is NormalIndicatorViewHolder) {
            holderIndicator.iv.imageTintList = ColorStateList.valueOf(unSelectedColor)
            holderIndicator.tvTitle.setTextColor(unSelectedColor)
        }else{
            if(autoCenterViewHolder != null){
                autoCenterViewHolder?.notifyDataSetChanged()
            }else{
                val centerViewHolder = holderIndicator as CenterIndicatorViewHolder
                centerViewHolder.notifyDataSetChanged()
            }
        }
    }

    override fun reSelectedStatus(holderIndicator: BaseIndicatorViewHolder) {

    }
}

open class CenterIndicatorViewHolder(itemLayoutId: Int) : BaseIndicatorViewHolder(itemLayoutId) {

    override fun bindView(itemView: View) {

    }

    open fun notifyDataSetChanged() {}
}

class NormalIndicatorViewHolder(itemLayoutId: Int) : BaseIndicatorViewHolder(itemLayoutId) {


    lateinit var tvTitle: TextView
    lateinit var iv: ImageView
    lateinit var view: View

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        iv = itemView.findViewById(R.id.iv)
        view = itemView.findViewById(R.id.view_round)
    }
}

