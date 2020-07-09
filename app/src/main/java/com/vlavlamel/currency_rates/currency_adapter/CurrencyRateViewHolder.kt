package com.vlavlamel.currency_rates.currency_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.base_adapter.BaseViewHolder
import com.vlavlamel.currency_rates.databinding.ItemCurrencyRateBinding
import kotlin.random.Random

class CurrencyRateViewHolder(val binding: ItemCurrencyRateBinding) : BaseViewHolder(binding) {

    @SuppressLint("ClickableViewAccessibility")
    fun bind(item: Currency) {
        binding.countryIcon.setImageDrawable(binding.countryIcon.context.getDrawable(item.image))
        binding.currencyCode.text = item.code
        binding.currencyFullName.text = binding.currencyFullName.context.getString(item.fullName)
        binding.rate.setText(Random.nextInt(0, 1000).toString())
        binding.rate.setOnTouchListener { v, _ ->
            if (adapterPosition == 0) {
                return@setOnTouchListener v.performClick()
            } else {
                itemView.performClick()
                return@setOnTouchListener false
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