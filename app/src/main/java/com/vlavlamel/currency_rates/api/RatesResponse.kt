package com.vlavlamel.currency_rates.api

import java.math.BigDecimal

data class RatesResponse(val baseCurrency: String, val rates: Map<String, BigDecimal>)