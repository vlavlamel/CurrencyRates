package com.vlavlamel.currency_rates.currency_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import com.vlavlamel.currency_rates.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.base_adapter.BaseViewHolder
import com.vlavlamel.currency_rates.databinding.ItemCurrencyRateBinding
import com.vlavlamel.currency_rates.model.RateItem
import java.math.BigDecimal

class CurrencyRateViewHolder(private val binding: ItemCurrencyRateBinding) :
    BaseViewHolder(binding) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(item: RateItem) {
        binding.countryIcon.setImageDrawable(binding.countryIcon.context.getDrawable(item.currency.image))
        binding.currencyCode.text = item.currency.code
        binding.currencyFullName.text =
            binding.currencyFullName.context.getString(item.currency.fullName)
        if (binding.rate.text.toString() != item.rate.toString()) binding.rate.setText(item.rate.toString())
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
                adapterEventSubject.onNext(
                    AdapterEvent.RateInputEvent(
                        adapterPosition,
                        BigDecimal(text.toString())
                    )
                )
            }
        }
    }

    override fun onViewDetachedFromWindow() {
        if (adapterPosition == 0) itemView.hideKeyboard()
    }

    override fun onViewAttachedToWindow() {
    }

    override fun onViewClicked() {
        binding.rate.requestFocus()
        binding.rate.showKeyboard()
    }

    fun View.showKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                ?: return
        inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}