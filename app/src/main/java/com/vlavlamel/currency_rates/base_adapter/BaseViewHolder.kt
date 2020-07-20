package com.vlavlamel.currency_rates.base_adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewHolder(_binding: ViewBinding) : RecyclerView.ViewHolder(_binding.root) {
    protected var adapterEventSubject: PublishSubject<AdapterEvent>? = null
    open fun attachAdapterEvent(adapterEventSubject: PublishSubject<AdapterEvent>) {
        this.adapterEventSubject = adapterEventSubject
        itemView.setOnClickListener {
            adapterEventSubject.onNext(AdapterEvent.ClickEvent(adapterPosition))
            onViewClicked()
        }
    }

    open fun onViewDetachedFromWindow() {}
    open fun onViewAttachedToWindow() {}
    open fun onViewClicked() {}
}