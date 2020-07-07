package com.vlavlamel.currency_rates.currency_adapter

import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.databinding.ItemCuurencyRateBinding
import kotlin.random.Random

class CurrencyRateViewHolder( val binding: ItemCuurencyRateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Currency) {
        binding.countryIcon.setImageDrawable(binding.countryIcon.context.getDrawable(item.image))
        binding.currencyCode.text = item.code
        binding.currencyFullName.text = binding.currencyFullName.context.getString(item.fullName)
        binding.appCompatEditText.setText(Random.nextInt(0, 10000).toString())
    }

    fun onViewDetachedFromWindow() {
        binding.appCompatEditText.isEnabled = false
    }

    fun onViewAttachedToWindow() {
        binding.appCompatEditText.isEnabled = adapterPosition == 0
    }

}