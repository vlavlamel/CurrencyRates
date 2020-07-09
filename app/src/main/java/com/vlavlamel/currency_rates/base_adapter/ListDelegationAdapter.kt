package com.vlavlamel.currency_rates.base_adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

abstract class ListDelegationAdapter<T> : BaseDelegationAdapter<List<T>> {

    private val differ: AsyncListDiffer<T>

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        delegatesManager: AdapterDelegatesManager<List<T>>
    ) : super(delegatesManager) {
        differ = AsyncListDiffer(this, diffCallback)
    }

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        vararg delegates: AdapterDelegate<List<T>>
    ) : super(*delegates) {
        differ = AsyncListDiffer(this, diffCallback)
    }

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super() {
        differ = AsyncListDiffer(this, diffCallback)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItems(): List<T> = differ.currentList

    override fun setItems(items: List<T>) {
        differ.submitList(items)
    }

    fun setItems(items: List<T>, runnable: () -> Unit) {
        differ.submitList(items, runnable)
    }
}