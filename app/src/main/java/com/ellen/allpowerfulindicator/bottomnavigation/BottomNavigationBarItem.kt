package com.ellen.allpowerfulindicator.bottomnavigation

import com.ellen.allpowerfulindicator.R
import com.ellen.indicator.ItemTab
import com.ellen.indicator.Mode

class BottomNavigationBarItem : ItemTab(){

    init {
        itemLayout = R.layout.item_bottom_navigation_bar
        itemMode = Mode.MODE_FIXED
    }

}