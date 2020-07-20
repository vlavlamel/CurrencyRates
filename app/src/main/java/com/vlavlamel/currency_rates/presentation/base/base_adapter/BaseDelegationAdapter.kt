package com.vlavlamel.currency_rates.presentation.base.base_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.PublishSubject

/*
* Main idea and expiration from - https://github.com/sockeqwe/AdapterDelegates
* The main advantage of this solution is that you can easily add viewholders with different viewtypes
* */

abstract class BaseDelegationAdapter<T>(protected val delegatesManager: AdapterDelegatesManager<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val adapterEventSubject: PublishSubject<AdapterEvent> =
        PublishSubject.create<AdapterEvent>()

    constructor() : this(AdapterDelegatesManager<T>())

    constructor(vararg delegates: AdapterDelegate<T>) : this(AdapterDelegatesManager<T>(*delegates))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType).apply {
            if (this is BaseViewHolder) {
                attachAdapterEvent(adapterEventSubject)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(getItems(), position, holder, null)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        delegatesManager.onBindViewHolder(getItems(), position, holder, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(getItems(), position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return delegatesManager.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    abstract fun getItems(): T

    abstract fun setItems(items: T)
}