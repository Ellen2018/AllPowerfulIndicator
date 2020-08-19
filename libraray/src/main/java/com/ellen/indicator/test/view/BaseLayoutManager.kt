package com.ellen.indicator.test.view

import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import com.ellen.indicator.test.Adapter

internal abstract class BaseLayoutManager<T : BaseIndicatorViewHolder> {

    lateinit var adapter: Adapter<T>

    lateinit var linearLayout: AllPowerfulLinearLayout
    lateinit var beforeFrameLayout: FrameLayout
    lateinit var afterFrameLayout: FrameLayout
    lateinit var viewStub: ViewStub
    lateinit var beforeView: BaseTrackView
    lateinit var afterView: BaseTrackView
}

internal class VManager<T : BaseIndicatorViewHolder> : BaseLayoutManager<T>() {

    lateinit var vScrollView: ScrollView

}

internal class HManage<T : BaseIndicatorViewHolder> : BaseLayoutManager<T>() {

    lateinit var hScrollView: HorizontalScrollView

}

internal class FixedManger<T : BaseIndicatorViewHolder> {
    lateinit var viewStub: ViewStub
    lateinit var adapter: Adapter<T>
    lateinit var linearLayout: FixedLinearLayout
}
