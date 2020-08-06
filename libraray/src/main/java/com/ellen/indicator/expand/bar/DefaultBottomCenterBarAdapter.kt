package com.ellen.indicator.expand.bar

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.BaseViewHolder
import com.ellen.libraray.R

class DefaultBottomCenterBarAdapter() : BaseBottomBarAdapter<CenterViewHolder, NormalViewHolder>() {

    var defaultTabs: MutableList<DefaultTab> = ArrayList()
    var autoCenterViewHolder: CenterViewHolder? = null
    var centerImageResource: Int? = null
        set(value) {
            if (field != value) {
                isChange = true
            }
            field = value
        }
    var isContainsCenter = false
    var onDefaultBottomTabSelectListener: OnDefaultBottomTabSelectListener? = null
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

    init {
        onTabSelectListener = object : OnTabSelectListener<CenterViewHolder, NormalViewHolder> {
            override fun onTabReselected(truePosition: Int, holder: NormalViewHolder) {
                onNormalReSelected(holder)
                onDefaultBottomTabSelectListener?.onTabReselected(truePosition, holder)
            }

            override fun onTabUnselected(truePosition: Int, holder: NormalViewHolder) {
                onNormalUnSelected(holder)
                onDefaultBottomTabSelectListener?.onTabUnselected(truePosition, holder)
            }

            override fun onTabSelected(truePosition: Int, holder: NormalViewHolder) {
                onNormalSelected(holder)
                onDefaultBottomTabSelectListener?.onTabSelected(truePosition, holder)
            }

            override fun onCenterTabListener(holder: CenterViewHolder) {
                onDefaultBottomTabSelectListener?.onCenterTabListener(holder)
            }
        }
    }

    constructor(defaultTabs: MutableList<DefaultTab>, centerImageResource: Int) : this() {
        this.centerImageResource = centerImageResource
        this.defaultTabs.addAll(defaultTabs)
    }

    override fun getCenterViewHolder(): CenterViewHolder? {
        return if (isContainsCenter) {
            if (autoCenterViewHolder != null) {
                return autoCenterViewHolder
            } else {
                CenterViewHolder(R.layout.item_view_center)
            }
        } else {
            null
        }
    }

    private fun onNormalSelected(holder: NormalViewHolder) {
        holder.iv.imageTintList = ColorStateList.valueOf(selectedColor)
        holder.tvTitle.setTextColor(selectedColor)
    }

    private fun onNormalUnSelected(holder: NormalViewHolder) {
        holder.iv.imageTintList = ColorStateList.valueOf(unSelectedColor)
        holder.tvTitle.setTextColor(unSelectedColor)
    }

    private fun onNormalReSelected(holder: NormalViewHolder) {}

    override fun getNormalViewHolder(): NormalViewHolder {
        return NormalViewHolder(R.layout.item_view_normal)
    }

    override fun showContentNormal(truePosition: Int, holder: NormalViewHolder) {
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

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        if (isChange) {
            for (position in 0 until getItemSize()) {
                val baseViewHolder =
                    allPowerIndicator.getTabAt(position)?.customView?.tag as BaseViewHolder
                if (baseViewHolder is NormalViewHolder) {
                    if(currentPosition == position) {
                        onNormalSelected(baseViewHolder)
                    }else{
                        onNormalUnSelected(baseViewHolder)
                    }
                }
            }
        }
        isChange = false
    }

    override fun initTab(holder: BaseViewHolder) {
        if (holder is NormalViewHolder) {
            onNormalUnSelected(holder)
        }
    }

    override fun showContentCenter(holder: CenterViewHolder) {
        if (autoCenterViewHolder == null) {
            centerImageResource?.let {
                holder.itemView?.findViewById<ImageView>(R.id.iv)?.setImageResource(it)
            }
        } else {
            autoCenterViewHolder?.notifyDataSetChanged()
        }
    }
}

open class CenterViewHolder(itemLayoutId: Int) : BaseViewHolder(itemLayoutId) {

    override fun bindView(itemView: View) {

    }

    open fun notifyDataSetChanged() {}
}

class NormalViewHolder(itemLayoutId: Int) : BaseViewHolder(itemLayoutId) {


    lateinit var tvTitle: TextView
    lateinit var iv: ImageView
    lateinit var view: View

    override fun bindView(itemView: View) {
        tvTitle = itemView.findViewById(R.id.tv_title)
        iv = itemView.findViewById(R.id.iv)
        view = itemView.findViewById(R.id.view_round)
    }
}

interface OnDefaultBottomTabSelectListener {
    fun onTabReselected(position: Int, holder: NormalViewHolder) {}
    fun onTabUnselected(position: Int, holder: NormalViewHolder) {}
    fun onTabSelected(position: Int, holder: NormalViewHolder) {}
    fun onCenterTabListener(holder: CenterViewHolder)
}
