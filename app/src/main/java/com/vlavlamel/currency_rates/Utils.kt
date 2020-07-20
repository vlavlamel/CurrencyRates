package com.vlavlamel.currency_rates

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

object Utils {
    fun pxToDp(px: Int): Int = px / Resources.getSystem().displayMetrics.density.toInt()

    fun dpToPx(dp: Int): Int = dp * Resources.getSystem().displayMetrics.density.toInt()

    fun <E> MutableList<E>.makeFirst(element: E) {
        remove(element)
        addFirst(element)
    }

    fun <E> MutableList<E>.addFirst(element: E) {
        add(0, element)
    }

    fun RecyclerView.scrollToTop() {
        (this.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 1)
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

    fun BigDecimal.currencyFormat(): String =
        NumberFormat.getCurrencyInstance().format(this).dropLast(2)

    fun String.currencyFormat(): BigDecimal = DecimalFormat().apply {
        isParseBigDecimal = true
    }.parse(this) as BigDecimal
}