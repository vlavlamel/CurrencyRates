package com.vlavlamel.currency_rates.base_adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder(_binding: ViewBinding) : RecyclerView.ViewHolder(_binding.root) {
    open fun onViewDetachedFromWindow() {}
    open fun onViewAttachedToWindow() {}
    open fun onViewClicked() {}
}