package com.ellen.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ellen.indicator.horizontal.Adapter
import com.ellen.indicator.horizontal.BaseIndicatorViewHolder

class VerticalIndicator : RecyclerView {

    init {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = linearLayoutManager
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

}

private class VerticalIndicatorAdapter<T : BaseIndicatorViewHolder>(var adapter: Adapter<T>) :
    RecyclerView.Adapter<VerticalIndicatorAdapter.VerticalIndicatorViewHolder>() {

    class VerticalIndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalIndicatorViewHolder {
        val baseIndicatorViewHolder: T? = adapter.getViewHolder(viewType)
        val view: View = baseIndicatorViewHolder?.layoutId?.let {
            LayoutInflater.from(parent.context).inflate(
                it, parent, false
            )
        }!!
        val holder = VerticalIndicatorViewHolder(view)
        holder.itemView.tag = baseIndicatorViewHolder
        return holder
    }

    override fun getItemCount(): Int {
        return adapter.getItemSize()
    }

    override fun onBindViewHolder(holder: VerticalIndicatorViewHolder, position: Int) {
        val baseIndicatorViewHolder: T? = holder.itemView.tag as T?
        baseIndicatorViewHolder?.let { adapter.showContent(it) }
    }

}