package com.ellen.indicator.vertical

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.vertical.view.AllPowerIndicatorRecyclerView
import com.ellen.indicator.vertical.view.MyLinearLayoutManager
import com.ellen.libraray.R


class AllPowerfulIndicator : FrameLayout, Indicator {

    var orientation: Orientation = Orientation.VERTICAL
    var currentItem = 0
    set(value) {
        field = value
        allPowerIndicatorAdapter.selectStatus.setPosition(value)
        notifyDataSetChanged()
    }
    var mode = Mode.SCROLL
    var onTabClickListener:OnTabClickListener? = null

    /**
     * 点击后焦点item的居中，居左，局右,居上，居下
     * 默认是居中效果
     */
    var clickGravity:ClickGravity = ClickGravity.BOTTOM
    var clickDeviation = 0
    private var viewPager:ViewPager? = null
    private var viewPager2:ViewPager2? = null
    internal var isFixedReset = false
    internal var isFirstDraw = true
    private lateinit var allPowerIndicatorAdapter:AllPowerIndicatorAdapter<*>
    internal lateinit var recyclerView:AllPowerIndicatorRecyclerView
    internal lateinit var hManager: HManager
    internal lateinit var vManager: VManager
    private  var mContentView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_all_powerful_indicator,this,true)

    constructor(context: Context) : super(context){

    }
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

    private fun <T : BaseIndicatorViewHolder> initRecyclerViewAdapter(adapter: Adapter<T>)
            : AllPowerIndicatorAdapter<T>{
        //init()
        adapter.onAttachAllPowerfulIndicator(this)
        val allPowerIndicatorAdapter =
            AllPowerIndicatorAdapter(this, adapter)
        this.allPowerIndicatorAdapter = allPowerIndicatorAdapter
        allPowerIndicatorAdapter.onItemClickListener = object : OnItemClickListener<T> {

            override fun onClickStatusItem(position: Int, holder: T) {
                if (position == adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.getPosition())) {
                    adapter.reSelectedStatus(position, holder)
                    adapter.onTabClickListener?.onTabReSelectedClick(position, holder)
                    onTabClickListener?.onTabReSelectedClick(position, holder)
                } else {
                    adapter.onTabClickListener?.onTabUnSelectedClick(
                        adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.getPosition()),
                        holder
                    )
                    onTabClickListener?.onTabUnSelectedClick(adapter.outStatusPosition(allPowerIndicatorAdapter.selectStatus.getPosition()), holder)
                    viewPager?.currentItem = position
                    viewPager2?.currentItem = position

                    //自由模式下点击响应
                    allPowerIndicatorAdapter.selectStatus.setPosition(adapter.inStatusPosition(position))
                    allPowerIndicatorAdapter.notifyDataSetChanged()

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
        adapter: Adapter<T>,
        viewPager2: ViewPager2
    ) {
        this.viewPager2 = viewPager2
        this.viewPager = null
        val allPowerIndicatorAdapter = initRecyclerViewAdapter(adapter)
        allPowerIndicatorAdapter.mandatorySize = this.viewPager2?.adapter?.itemCount
        recyclerView.adapter = allPowerIndicatorAdapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                allPowerIndicatorAdapter.selectStatus.setPosition(adapter.inStatusPosition(position))
                allPowerIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: Adapter<T>,
        viewPager: ViewPager
    ) {
        this.viewPager = viewPager
        this.viewPager2 = null
        val allPowerIndicatorAdapter = initRecyclerViewAdapter(adapter)
        allPowerIndicatorAdapter.mandatorySize = this.viewPager?.adapter?.count
        recyclerView.adapter = allPowerIndicatorAdapter
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
                allPowerIndicatorAdapter.selectStatus.setPosition(adapter.inStatusPosition(position))
                allPowerIndicatorAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun <T : BaseIndicatorViewHolder> bindFree(adapter: Adapter<T>) {
        recyclerView.adapter = initRecyclerViewAdapter(adapter)
    }

    override fun notifyDataSetChanged() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

//    private fun init() {
//       if(orientation == Orientation.HORIZONTAL){
//           val viewStub = mContentView.findViewById<ViewStub>(R.id.vs_h)
//           viewStub.inflate()
//           val horizontalScrollView= mContentView.findViewById<HorizontalScrollView>(R.id.h_scroll_view)
//           val hRecyclerView = mContentView.findViewById<AllPowerIndicatorRecyclerView>(R.id.h_recycler_view)
//           hManager = HManager(viewStub,horizontalScrollView,hRecyclerView)
//           recyclerView = hRecyclerView
//           viewStub.visibility = View.VISIBLE
//       }else{
//           val viewStub = mContentView.findViewById<ViewStub>(R.id.vs_v)
//           viewStub.inflate()
//           val verticalScrollView= mContentView.findViewById<ScrollView>(R.id.v_scroll_view)
//           val vRecyclerView = mContentView.findViewById<AllPowerIndicatorRecyclerView>(R.id.v_recycler_view)
//           val rlTrackView = mContentView.findViewById<RelativeLayout>(R.id.rl_track_view)
//           vManager = VManager(viewStub,verticalScrollView,rlTrackView,vRecyclerView)
//           recyclerView = vRecyclerView
//           viewStub.visibility = View.VISIBLE
//       }
//       recyclerView.allPowerfulIndicator = this
//    }

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

    enum class ClickGravity{
        CENTER,LEFT,RIGHT,TOP,BOTTOM
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

internal class HManager(var vsHorizontal:ViewStub,
                        var horizontalScrollView: HorizontalScrollView,
                        var horizontalRecyclerView: AllPowerIndicatorRecyclerView){

    init {
        val linearLayoutManager = MyLinearLayoutManager(horizontalRecyclerView.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        horizontalRecyclerView.layoutManager = linearLayoutManager
    }
}

internal class VManager(var vsVertical:ViewStub,
                        var verticalScrollView: ScrollView,
                        var verticalRlTrackView:RelativeLayout,
                        var VerticalRecyclerView: AllPowerIndicatorRecyclerView){

    init {
        val linearLayoutManager = MyLinearLayoutManager(VerticalRecyclerView.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        VerticalRecyclerView.layoutManager = linearLayoutManager
    }

}
