package com.vlavlamel.currency_rates.currency_adapter

import androidx.recyclerview.widget.DiffUtil
import com.vlavlamel.currency_rates.model.RateItem

class RatesItemCallback : DiffUtil.ItemCallback<RateItem>() {
    override fun areItemsTheSame(oldItem: RateItem, newItem: RateItem): Boolean =
        oldItem.currency == newItem.currency

    override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: RateItem, newItem: RateItem): Any? =
        if (oldItem.rate != newItem.rate) newItem.rate else null
}