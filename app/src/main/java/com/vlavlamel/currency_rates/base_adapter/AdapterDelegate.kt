package com.vlavlamel.currency_rates.base_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegate<T> {
    abstract fun isForViewType(items: T, position: Int): Boolean

    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindViewHolder(
        items: T,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    )

    open fun onViewRecycled(holder: RecyclerView.ViewHolder) {}

    open fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean = false

    open fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is BaseViewHolder) {
            holder.onViewAttachedToWindow()
        }
    }

    open fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is BaseViewHolder) {
            holder.onViewDetachedFromWindow()
        }
    }
}