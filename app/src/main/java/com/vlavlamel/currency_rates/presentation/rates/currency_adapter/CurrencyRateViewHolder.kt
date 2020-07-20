package com.vlavlamel.currency_rates.presentation.rates.currency_adapter

import android.annotation.SuppressLint
import androidx.core.widget.doOnTextChanged
import com.vlavlamel.currency_rates.Utils.currencyFormat
import com.vlavlamel.currency_rates.Utils.hideKeyboard
import com.vlavlamel.currency_rates.Utils.showKeyboard
import com.vlavlamel.currency_rates.databinding.ItemCurrencyRateBinding
import com.vlavlamel.currency_rates.presentation.base.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.presentation.base.base_adapter.BaseViewHolder
import com.vlavlamel.currency_rates.presentation.rates.currency_adapter.item.RateItem

class CurrencyRateViewHolder(private val binding: ItemCurrencyRateBinding) :
    BaseViewHolder(binding) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(item: RateItem) {
        binding.countryIcon.setImageDrawable(binding.countryIcon.context.getDrawable(item.currency.image))
        binding.currencyCode.text = item.currency.code
        binding.currencyFullName.text =
            binding.currencyFullName.context.getString(item.currency.fullName)
        if (binding.rate.text.toString() != item.rate) binding.rate.setText(item.rate)
        binding.rate.setOnTouchListener { v, _ ->
            if (adapterPosition == 0) {
                return@setOnTouchListener v.performClick()
            } else {
                itemView.performClick()
                return@setOnTouchListener false
            }
        }
        binding.rate.doOnTextChanged { text, start, before, count ->
            if (adapterPosition == 0) {
                adapterEventSubject?.onNext(
                    AdapterEvent.RateInputEvent(
                        adapterPosition,
                        text.toString().currencyFormat()
                    )
                )
            }
        }
    }

    override fun onViewDetachedFromWindow() {
        if (adapterPosition == 0) itemView.hideKeyboard()
    }

    override fun onViewClicked() {
        binding.rate.requestFocus()
        binding.rate.showKeyboard()
    }
}