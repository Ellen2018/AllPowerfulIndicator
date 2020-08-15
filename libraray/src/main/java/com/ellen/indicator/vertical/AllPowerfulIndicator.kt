package com.ellen.indicator.vertical

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2


class AllPowerfulIndicator : RecyclerView, Indicator {

    var orientation: Orientation = Orientation.VERTICAL
    var mode = Mode.SCROLL
    var onTabClickListener:OnTabClickListener? = null
    private var viewPager:ViewPager? = null
    private var viewPager2:ViewPager2? = null
    internal var isFixedReset = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet) {

    }

    private fun <T : BaseIndicatorViewHolder> initRecyclerViewAdapter(adapter: com.ellen.indicator.vertical.Adapter<T>)
            : AllPowerIndicatorAdapter<T>{
        adapter.bindAllPowerfulIndicator(this)
        val linearLayoutManager = LinearLayoutManager(context)
        if (orientation == Orientation.HORIZONTAL) {
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        } else {
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        }
        layoutManager = linearLayoutManager
        val allPowerIndicatorAdapter: AllPowerIndicatorAdapter<T> =
            AllPowerIndicatorAdapter(this, adapter)
        allPowerIndicatorAdapter.onItemClickListener = object : OnItemClickListener<T> {

            override fun onClickStatusItem(position: Int, holder: T) {
                if (position == adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.selectedPosition)) {
                    adapter.reSelectedStatus(position, holder)
                    adapter.onTabClickListener?.onTabReSelectedClick(position, holder)
                    onTabClickListener?.onTabReSelectedClick(position, holder)
                } else {
                    adapter.onTabClickListener?.onTabUnSelectedClick(
                        adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.selectedPosition),
                        holder
                    )
                    onTabClickListener?.onTabUnSelectedClick(adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.selectedPosition), holder)
                    viewPager?.currentItem = position
                    viewPager2?.currentItem = position
                    //自由模式下点击响应
                    if (viewPager == null && viewPager2 == null) {
                        allPowerIndicatorAdapter.selectStatus.selectedPosition = adapter.inStatusPosition(position)
                        allPowerIndicatorAdapter.notifyDataSetChanged()
                    }
                    adapter.onTabClickListener?.onTabSelectedClick(
                        position,
                        holder
                    )
                    onTabClickListener?.onTabSelectedClick(position, holder)
                }
            }

            override fun onClickNoStatusItem(position: Int, holder: T) {
                adapter.onTabClickListener?.onNoStatusTabClick(position,holder)
                onTabClickListener?.onNoStatusTabClick(position,holder)
            }
        }
        return allPowerIndicatorAdapter
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager2(
        adapter: com.ellen.indicator.vertical.Adapter<T>,
        viewPager2: ViewPager2
    ) {
        this.viewPager2 = viewPager2
        this.viewPager = null
        val allPowerIndicatorAdapter = initRecyclerViewAdapter(adapter)
        this.adapter = allPowerIndicatorAdapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                allPowerIndicatorAdapter.selectStatus.selectedPosition = adapter.inStatusPosition(position)
                allPowerIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: com.ellen.indicator.vertical.Adapter<T>,
        viewPager: ViewPager
    ) {
        this.viewPager = viewPager
        this.viewPager2 = null
        val allPowerIndicatorAdapter = initRecyclerViewAdapter(adapter)
        this.adapter = allPowerIndicatorAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                allPowerIndicatorAdapter.selectStatus.selectedPosition = adapter.inStatusPosition(position)
                allPowerIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun <T : BaseIndicatorViewHolder> bindFree(adapter: com.ellen.indicator.vertical.Adapter<T>) {
        this.adapter = initRecyclerViewAdapter(adapter)
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (!isFixedReset && mode == Mode.FIXED) {
            val viewGroup = this.parent as ViewGroup
            val marginWidth = marginLeft + marginRight + paddingLeft + paddingRight
            val marginHeight = marginTop + marginBottom + paddingTop + paddingBottom
            val verticalIndicatorAdapter = adapter as AllPowerIndicatorAdapter<*>
            verticalIndicatorAdapter.width = viewGroup.width - marginWidth
            verticalIndicatorAdapter.height = viewGroup.height - marginHeight
            isFixedReset = true
            verticalIndicatorAdapter.notifyDataSetChanged()
        }
    }

    enum class Orientation {
        HORIZONTAL, VERTICAL;
    }

    enum class Mode {
        FIXED,
        AUTO,
        SCROLL;
    }

    interface OnTabClickListener{
        fun onTabSelectedClick(position: Int,holder: BaseIndicatorViewHolder)
        fun onTabUnSelectedClick(position: Int,holder: BaseIndicatorViewHolder)
        fun onTabReSelectedClick(position: Int,holder: BaseIndicatorViewHolder)
        fun onNoStatusTabClick(position: Int,holder: BaseIndicatorViewHolder){}
    }

}

private interface Indicator {
    fun <T : BaseIndicatorViewHolder> bindViewPager2(
        adapter: Adapter<T>,
        viewPager2: ViewPager2
    )

    fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: Adapter<T>,
        viewPager: ViewPager
    )

    fun <T : BaseIndicatorViewHolder> bindFree(
        adapter: Adapter<T>
    )

    fun notifyDataSetChanged()
}
