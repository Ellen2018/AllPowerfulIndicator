package com.ellen.indicator

interface Adapter<T:BaseViewHolder> {
    fun getItemType(position: Int): Int
    fun getViewHolder(viewType: Int): T
    fun getItemSize(): Int
    fun showContent(holder:T)
    fun onTabReselected(holder:T)
    fun onTabUnselected(holder:T)
    fun onTabSelected(holder:T)
    fun bindLinkageView(allPowerIndicator: AllPowerIndicator)
}


