package com.vlavlamel.currency_rates.base_adapter

abstract class ListDelegationAdapter<T> : BaseDelegationAdapter<List<T>> {

    constructor(delegatesManager: AdapterDelegatesManager<List<T>>) : super(delegatesManager)
    constructor(vararg delegates: AdapterDelegate<List<T>>) : super(*delegates)
    constructor() : super()

    override fun getItemCount(): Int = items?.size ?: 0
}