package com.ellen.indicator.test

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
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
import com.ellen.indicator.test.view.*
import com.ellen.indicator.test.view.BaseLayoutManager
import com.ellen.indicator.test.view.FixedManger
import com.ellen.indicator.test.view.HManage
import com.ellen.indicator.test.view.VManager
import com.ellen.libraray.R

class AllPowerfulIndicator : FrameLayout, Indicator {

    var currentItem: Int = 0
        set(value) {
            field = value
            if (viewPager != null) {
                viewPager?.currentItem = value
            }
            if (viewPager2 != null) {
                viewPager2?.currentItem = value
            }
            if (viewPager == null && viewPager2 == null) {
                if (freeFirstInit) {
                    baseLayoutManager.linearLayout.selectPosition(value)
                }
            }
        }

    private var isUserClick = true
    private var freeFirstInit = false
    private var isFirstDraw = false
    private var viewPager: ViewPager? = null
    private var viewPager2: ViewPager2? = null
    var clickD = 0
    var mode: Mode = Mode.FIXED
    var clickGravity: ClickGravity = ClickGravity.CENTER
    private var mContentView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_all_powerful_indicator, this, true)

    var orientation: Orientation = Orientation.VERTICAL
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

    enum class ClickGravity {
        CENTER,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        NONE
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager2(
        adapter: Adapter<T>,
        viewPager2: ViewPager2,
        initItemPosition: Int
    ) {
        adapter.onAttach(this)
        this.viewPager2 = viewPager2
        currentItem = initItemPosition
        adapter.statusManager.selectPosition = adapter.inStatusPosition(initItemPosition)
        initLayoutManager(adapter)
        adapter.trueCount = viewPager2.adapter?.itemCount!! + adapter.getNoStatusItemCount()

        if(mode == Mode.FIXED) {
            fixedManger.linearLayout.onAttachAdapter(adapter)
        }else{
            baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        }
        adapter.addOnTabClickListener(object : OnTabClickListener<T> {
            override fun onTabSelectedClick(position: Int, holder: T) {
                isUserClick = true
                viewPager2.currentItem = position
                isUserClick = false
            }

            override fun onTabUnSelectedClick(position: Int, holder: T) {

            }

            override fun onTabReSelectedClick(position: Int, holder: T) {

            }
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (!isUserClick) {
                    if(mode == Mode.FIXED){
                        fixedManger.linearLayout.selectPosition(position)
                    }else {
                        baseLayoutManager.linearLayout.selectPosition(position)
                    }
                }
            }
        })
    }

    override fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: Adapter<T>,
        viewPager: ViewPager,
        initItemPosition: Int
    ) {
        adapter.onAttach(this)
        this.viewPager = viewPager
        currentItem = initItemPosition
        adapter.statusManager.selectPosition = adapter.inStatusPosition(initItemPosition)
        initLayoutManager(adapter)
        adapter.trueCount = viewPager.adapter?.count!! + adapter.getNoStatusItemCount()
        if(mode == Mode.FIXED) {
            fixedManger.linearLayout.onAttachAdapter(adapter)
        }else{
            baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        }
        adapter.addOnTabClickListener(object : OnTabClickListener<T> {
            override fun onTabSelectedClick(position: Int, holder: T) {
                isUserClick = true
                viewPager.currentItem = position
                isUserClick = false
            }

            override fun onTabUnSelectedClick(position: Int, holder: T) {

            }

            override fun onTabReSelectedClick(position: Int, holder: T) {

            }
        })
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
                if (!isUserClick) {
                    if(mode == Mode.FIXED){
                        fixedManger.linearLayout.selectPosition(position)
                    }else {
                        baseLayoutManager.linearLayout.selectPosition(position)
                    }
                }
            }
        })
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
        baseLayoutManager.linearLayout.onAttachAdapter(adapter)
        freeFirstInit = true
    }

    override fun notifyDataSetChanged() {
        baseLayoutManager.linearLayout.notifyDataSetChanged()
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
                vManager.afterView = LineTrackView().onCreateTrackView(LayoutInflater.from(context),vManager.afterFrameLayout)
                vManager.afterFrameLayout.addView(vManager.afterView)

                vManager.beforeFrameLayout.visibility = View.VISIBLE
                vManager.beforeView = LineTrackView().onCreateTrackView(LayoutInflater.from(context),vManager.beforeFrameLayout)
                vManager.beforeFrameLayout.addView(vManager.beforeView)

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
                hManager.afterView = LineTrackView().onCreateTrackView(LayoutInflater.from(context),hManager.afterFrameLayout)
                hManager.afterFrameLayout.addView(hManager.afterView)

                hManager.beforeFrameLayout.visibility = View.VISIBLE
                hManager.beforeView = LineTrackView().onCreateTrackView(LayoutInflater.from(context),hManager.beforeFrameLayout)
                hManager.beforeFrameLayout.addView(hManager.beforeView)
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
        viewPager2: ViewPager2,
        initItemPosition: Int = 0
    )

    fun <T : BaseIndicatorViewHolder> bindViewPager(
        adapter: Adapter<T>,
        viewPager: ViewPager,
        initItemPosition: Int = 0
    )

    fun <T : BaseIndicatorViewHolder> bindFree(
        adapter: Adapter<T>, initItemPosition: Int = 0
    )

    fun notifyDataSetChanged()
}