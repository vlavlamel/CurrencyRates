package com.vlavlamel.currency_rates.currency_adapter

import com.vlavlamel.currency_rates.base_adapter.ListDelegationAdapter
import com.vlavlamel.currency_rates.model.RateItem

class CurrencyRatesAdapter : ListDelegationAdapter<RateItem>(RatesItemCallback()) {
    init {
        delegatesManager.addDelegate(CurrencyRateDelegate())
    }
}