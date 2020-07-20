package com.vlavlamel.currency_rates.presentation.base.base_adapter

import java.math.BigDecimal

sealed class AdapterEvent {
    data class ClickEvent(val position: Int) : AdapterEvent()
    data class RateInputEvent(val position: Int, val rate: BigDecimal) : AdapterEvent()
}