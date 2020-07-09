package com.vlavlamel.currency_rates.model

import com.vlavlamel.currency_rates.Currency
import java.math.BigDecimal

data class RateItem(val currency: Currency, val rate: BigDecimal)