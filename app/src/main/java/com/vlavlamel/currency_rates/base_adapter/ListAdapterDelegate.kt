package com.vlavlamel.currency_rates.base_adapter

abstract class ListAdapterDelegate<T> : AdapterDelegate<List<T>>() {
    override fun isForViewType(items: List<T>, position: Int): Boolean =
        isForViewType(items[position], items, position)

    protected abstract fun isForViewType(
        item: T,
        items: List<T>,
        position: Int
    ): Boolean
}