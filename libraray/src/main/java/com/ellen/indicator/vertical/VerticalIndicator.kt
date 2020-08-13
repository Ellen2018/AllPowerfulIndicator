package com.ellen.indicator.vertical

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class VerticalIndicator : RecyclerView, Indicator {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun <T : BaseVerticalIndicatorViewHolder> bindViewPager2(
        adapter: com.ellen.indicator.vertical.Adapter<T>,
        viewPager2: ViewPager2
    ) {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = linearLayoutManager
        val verticalIndicatorAdapter: VerticalIndicatorAdapter<T> =
            VerticalIndicatorAdapter(adapter)
        verticalIndicatorAdapter.onItemClickListener = object : OnItemClickListener{
            override fun <T : BaseVerticalIndicatorViewHolder> onClickItem(
                position: Int,
                holder: T
            ) {
                viewPager2.currentItem = position
            }
        }
        this.adapter = verticalIndicatorAdapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                verticalIndicatorAdapter.selectStatus.selectedPosition = position
                verticalIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

}

private interface Indicator {
    fun <T : BaseVerticalIndicatorViewHolder> bindViewPager2(
        adapter: com.ellen.indicator.vertical.Adapter<T>,
        viewPager2: ViewPager2
    )
}
