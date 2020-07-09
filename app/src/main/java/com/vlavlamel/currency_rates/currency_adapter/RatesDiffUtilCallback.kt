package com.vlavlamel.currency_rates.currency_adapter

import androidx.recyclerview.widget.DiffUtil
import com.vlavlamel.currency_rates.Currency

class RatesDiffUtilCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        oldItem == newItem
}