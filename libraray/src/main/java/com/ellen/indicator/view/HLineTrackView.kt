package com.ellen.indicator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ellen.libraray.R

class HLineTrackView : BaseTrackView(){

    override fun onCreateTrackView(inflater: LayoutInflater, parent: ViewGroup): View {
        return inflater.inflate(R.layout.layout_h_line_trace_view,parent,false)
    }

}