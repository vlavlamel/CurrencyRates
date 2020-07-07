package com.vlavlamel.currency_rates.currency_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.base_adapter.ListAdapterDelegate
import com.vlavlamel.currency_rates.databinding.ItemCuurencyRateBinding

class CurrencyRateDelegate : ListAdapterDelegate<Currency>() {
    override fun isForViewType(item: Currency, items: List<Currency>, position: Int): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        CurrencyRateViewHolder(
            ItemCuurencyRateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        items: List<Currency>?,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) {
        (holder as CurrencyRateViewHolder).bind(items!![position])
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as CurrencyRateViewHolder).onViewDetachedFromWindow()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as CurrencyRateViewHolder).onViewAttachedToWindow()
    }
}