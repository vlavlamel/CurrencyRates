package com.vlavlamel.currency_rates.base_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/*
* Main idea and expiration from - https://github.com/sockeqwe/AdapterDelegates
* The main advantage of this solution is that you can easily add viewholders different viewtypes
* */

abstract class  BaseDelegationAdapter<T>(protected val delegatesManager: AdapterDelegatesManager<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //open val adapterEvents: MutableLiveData<AdapterEvent<T>> = MutableLiveData()

    var items: T? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    constructor() : this(AdapterDelegatesManager<T>())

    constructor(vararg delegates: AdapterDelegate<T>) : this(AdapterDelegatesManager<T>(*delegates))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder, null)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        delegatesManager.onBindViewHolder(items, position, holder, payloads)
        //declareAdapterEvent(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
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
//
//    open fun declareAdapterEvent(
//        holder: RecyclerView.ViewHolder, position: Int,
//        payloads: List<Any>
//    ) {
//
//    }
}