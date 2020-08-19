package com.ellen.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ellen.indicator.view.*
import com.ellen.indicator.view.BaseLayoutManager
import com.ellen.indicator.view.FixedManger
import com.ellen.indicator.view.HManage
import com.ellen.indicator.view.VManager
import com.ellen.libraray.R

/**
 * 注意第一次触发是ViewPager2或者ViewPager触发的
 */
class AllPowerfulIndicator : FrameLayout, Indicator {

    var currentItem: Int = 0
        set(value) {
            field = value
            inResponse(value)
        }
    get() {
        return if(mode == Mode.FIXED){
            val adapter = fixedManger.adapter
            adapter.outStatusPosition(adapter.statusManager.selectPosition)
        }else{
            val adapter = baseLayoutManager.adapter
            adapter.outStatusPosition(adapter.statusManager.selectPosition)
        }
    }

    private var freeFirstInit = false
    private var isFirstDraw = false
    private var viewPager: ViewPager? = null
    private lateinit var viewPagerPagerListener:ViewPager.OnPageChangeListener
    private var viewPager2: ViewPager2? = null
    private lateinit var viewPager2PageListener:ViewPager2.OnPageChangeCallback
    var clickD = 0
    var mode: Mode =
        Mode.SCROLL
    var clickGravity: ClickGravity =
        ClickGravity.CENTER
    var trackMode:TrackMode = TrackMode.NONE
    private var mContentView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_all_powerful_indicator, this, true)

    var orientation: Orientation =
        Orientation.VERTICAL
    internal lateinit var baseLayoutManager: BaseLayoutManager<*>
    internal lateinit var fixedManger: FixedManger<*>

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

    enum class Orientation {
        HORIZONTAL, VERTICAL;
    }

    enum class Mode {
        FIXED,
        AUTO,
        SCROLL;
    }

    enum class TrackMode{
        START,
        END,
        NONE,
        ALL
    }

    enum class ClickGravity {
        CENTER,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        NONE
    }

    /**
     * 外部响应触发
     * 点击了某个Tab
     */
    private fun outResponse(position:Int){
        viewPager?.removeOnPageChangeListener(viewPagerPagerListener)
        viewPager2?.unregisterOnPageChangeCallback(viewPager2PageListener)
        //调整ViewPager或者ViewPager2的位置
        viewPager?.currentItem = position
        viewPager2?.currentItem = position
        viewPager?.addOnPageChangeListener(viewPagerPagerListener)
        viewPager2?.registerOnPageChangeCallback(viewPager2PageListener)
    }

    private fun inResponse(position: Int){
        if(mode == Mode.FIXED){
            fixedManger.linearLayout.selectPosition(position)
        }else {
            baseLayoutManager.linearLayout.selectPosition(position)
        }
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager2(
        adapter: Adapter<T>,
        viewPager2: ViewPager2
    ) {
        adapter.onAttach(this)
        this.viewPager2 = viewPager2
        adapter.statusManager.selectPosition = adapter.inStatusPosition(viewPager2.currentItem)
        initLayoutManager(adapter)
        adapter.trueCount = viewPager2.adapter?.itemCount!! + adapter.getNoStatusItemCount()
        if(mode == Mode.FIXED) {
            fixedManger.linearLayout.onAttachAdapter(adapter)
        }else{
            baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        }
        adapter.addOnTabClickListener(object :
            OnTabClickListener<T> {
            override fun onTabSelectedClick(position: Int, holder: T) {
                outResponse(position)
            }

            override fun onTabUnSelectedClick(position: Int, holder: T) {

            }

            override fun onTabReSelectedClick(position: Int, holder: T) {

            }
        })
        viewPager2PageListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                inResponse(position)
            }
        }
        viewPager2.registerOnPageChangeCallback(viewPager2PageListener)
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: Adapter<T>,
        viewPager: ViewPager
    ) {
        adapter.onAttach(this)
        this.viewPager = viewPager
        adapter.statusManager.selectPosition = adapter.inStatusPosition(viewPager.currentItem)
        initLayoutManager(adapter)
        adapter.trueCount = viewPager.adapter?.count!! + adapter.getNoStatusItemCount()
        if(mode == Mode.FIXED) {
            fixedManger.linearLayout.onAttachAdapter(adapter)
        }else{
            baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        }
        adapter.addOnTabClickListener(object :
            OnTabClickListener<T> {
            override fun onTabSelectedClick(position: Int, holder: T) {
                outResponse(position)
            }

            override fun onTabUnSelectedClick(position: Int, holder: T) {

            }

            override fun onTabReSelectedClick(position: Int, holder: T) {

            }
        })
        viewPagerPagerListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                inResponse(position)
            }
        }
        viewPager.addOnPageChangeListener(viewPagerPagerListener)
    }

    override fun <T : BaseIndicatorViewHolder> bindFree(
        adapter: Adapter<T>,
        initItemPosition: Int
    ) {
        adapter.onAttach(this)
        currentItem = initItemPosition
        adapter.statusManager.selectPosition = adapter.inStatusPosition(initItemPosition)
        initLayoutManager(adapter)
        adapter.trueCount = adapter.getStatusItemCount() + adapter.getNoStatusItemCount()
        if(mode == Mode.FIXED) {
            fixedManger.linearLayout.onAttachAdapter(adapter)
        }else{
            baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        }
        freeFirstInit = true
    }

    override fun notifyDataSetChanged() {
        if(mode == Mode.FIXED){
            fixedManger.linearLayout.notifyDataSetChanged()
        }else {
            baseLayoutManager.linearLayout.notifyDataSetChanged()
        }
    }

    private fun <T : BaseIndicatorViewHolder> initLayoutManager(adapter: Adapter<T>) {
        if(mode == Mode.FIXED){
            val fixedManger = FixedManger<T>()
            fixedManger.adapter = adapter
            this.fixedManger = fixedManger
            if(orientation == Orientation.VERTICAL){
                mContentView.findViewById<ViewStub>(R.id.vs_h_fixed).visibility = View.GONE
                fixedManger.viewStub = mContentView.findViewById(R.id.vs_v_fixed)
                fixedManger.viewStub.inflate()
                fixedManger.linearLayout = mContentView.findViewById(R.id.v_fixed_linear_layout)
            }else{
                mContentView.findViewById<ViewStub>(R.id.vs_v_fixed).visibility = View.GONE
                fixedManger.viewStub = mContentView.findViewById(R.id.vs_h_fixed)
                fixedManger.viewStub.inflate()
                fixedManger.linearLayout = mContentView.findViewById(R.id.h_fixed_linear_layout)
            }
        }else {
            if (orientation == Orientation.VERTICAL) {
                val vManager = VManager<T>()
                baseLayoutManager = vManager
                vManager.adapter = adapter
                mContentView.findViewById<ViewStub>(R.id.vs_h).visibility = View.GONE
                vManager.viewStub = mContentView.findViewById(R.id.vs_v)
                vManager.viewStub.inflate()
                vManager.linearLayout = mContentView.findViewById(R.id.v_linear_layout)
                vManager.beforeFrameLayout = mContentView.findViewById(R.id.v_tracker_left)
                vManager.afterFrameLayout = mContentView.findViewById(R.id.v_tracker_right)
                vManager.vScrollView = mContentView.findViewById(R.id.v_scroll_view)

                vManager.afterFrameLayout.visibility = View.VISIBLE
                vManager.afterView = VLineTrackView()
                vManager.afterView.initContentView(LayoutInflater.from(context),vManager.afterFrameLayout)
                vManager.afterFrameLayout.addView(vManager.afterView.contentView)

                vManager.beforeFrameLayout.visibility = View.VISIBLE
                vManager.beforeView = VLineTrackView()
                vManager.beforeView.initContentView(LayoutInflater.from(context),vManager.beforeFrameLayout)
                vManager.beforeFrameLayout.addView(vManager.beforeView.contentView)

            } else {
                val hManager = HManage<T>()
                baseLayoutManager = hManager
                hManager.adapter = adapter
                mContentView.findViewById<ViewStub>(R.id.vs_v).visibility = View.GONE
                hManager.viewStub = mContentView.findViewById(R.id.vs_h)
                hManager.viewStub.inflate()
                hManager.linearLayout = mContentView.findViewById(R.id.h_linear_layout)
                hManager.beforeFrameLayout = mContentView.findViewById(R.id.h_tracker_top)
                hManager.afterFrameLayout = mContentView.findViewById(R.id.h_tracker_bottom)
                hManager.hScrollView = mContentView.findViewById(R.id.h_scroll_view)

                hManager.afterFrameLayout.visibility = View.VISIBLE
                hManager.afterView = HLineTrackView()
                hManager.afterView.initContentView(LayoutInflater.from(context),hManager.afterFrameLayout)
                hManager.afterFrameLayout.addView(hManager.afterView.contentView)

                hManager.beforeFrameLayout.visibility = View.VISIBLE
                hManager.beforeView = HLineTrackView()
                hManager.beforeView.initContentView(LayoutInflater.from(context),hManager.beforeFrameLayout)
                hManager.beforeFrameLayout.addView(hManager.beforeView.contentView)
            }
            when(trackMode){
                TrackMode.ALL-> {
                    baseLayoutManager.afterFrameLayout.visibility = View.VISIBLE
                    baseLayoutManager.beforeFrameLayout.visibility = View.VISIBLE
                }
                TrackMode.START->{
                    baseLayoutManager.afterFrameLayout.visibility = View.VISIBLE
                    baseLayoutManager.beforeFrameLayout.visibility = View.GONE
                }
                TrackMode.END->{
                    baseLayoutManager.afterFrameLayout.visibility = View.GONE
                    baseLayoutManager.beforeFrameLayout.visibility = View.VISIBLE
                }
                else->{
                    baseLayoutManager.afterFrameLayout.visibility = View.GONE
                    baseLayoutManager.beforeFrameLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if(mode != Mode.FIXED && !isFirstDraw){
            val parent = this.parent as ViewGroup
            val height =
                parent.height - (paddingTop + paddingBottom + parent.paddingTop + parent.paddingBottom + marginTop + marginBottom)
            val width =
                parent.width - (paddingLeft + paddingRight + parent.paddingLeft + parent.paddingRight + marginLeft + marginRight)
            baseLayoutManager.linearLayout.parentHeight = height
            baseLayoutManager.linearLayout.parentWidth = width
            baseLayoutManager.linearLayout.firstDrawCompete()
            isFirstDraw = true
        }
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
        adapter: Adapter<T>, initItemPosition: Int = 0
    )

    fun notifyDataSetChanged()
}