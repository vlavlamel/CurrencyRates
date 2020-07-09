package com.vlavlamel.currency_rates.currency_adapter

import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.base_adapter.ListDelegationAdapter

class CurrencyRatesAdapter : ListDelegationAdapter<Currency>(RatesDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(CurrencyRateDelegate())
    }
}