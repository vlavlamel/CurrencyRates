package com.vlavlamel.currency_rates

import android.content.res.Resources

object Utils {
    fun pxToDp(px: Int): Int = px / Resources.getSystem().getDisplayMetrics().density.toInt()

    fun dpToPx(dp: Int): Int = dp * Resources.getSystem().getDisplayMetrics().density.toInt()
}