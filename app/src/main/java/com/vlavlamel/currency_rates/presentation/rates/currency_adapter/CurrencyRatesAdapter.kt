package com.vlavlamel.currency_rates.presentation.rates.currency_adapter

import com.vlavlamel.currency_rates.presentation.base.base_adapter.ListDelegationAdapter
import com.vlavlamel.currency_rates.presentation.rates.currency_adapter.item.RateItem

class CurrencyRatesAdapter : ListDelegationAdapter<RateItem>(RatesItemCallback()) {
    init {
        delegatesManager.addDelegate(CurrencyRateDelegate())
    }
}