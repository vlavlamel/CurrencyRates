package com.vlavlamel.currency_rates

import android.content.res.Resources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object Utils {
    fun pxToDp(px: Int): Int = px / Resources.getSystem().getDisplayMetrics().density.toInt()

    fun dpToPx(dp: Int): Int = dp * Resources.getSystem().getDisplayMetrics().density.toInt()

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
}