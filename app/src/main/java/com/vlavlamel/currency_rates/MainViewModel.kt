package com.vlavlamel.currency_rates

import com.vlavlamel.currency_rates.model.RateItem
import com.vlavlamel.currency_rates.repo.RatesRepository
import io.reactivex.rxjava3.core.Observable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class MainViewModel(private val ratesRepository: RatesRepository) {
    var multiplier = BigDecimal.ONE
    var currentCurrency = Currency.EUR

    fun getRates(): Observable<List<RateItem>> = Observable.interval(0, 1, TimeUnit.SECONDS)
        .flatMap {
            ratesRepository.getRates(currentCurrency.code).map { response ->
                response.rates.map {
                    RateItem(
                        Currency.valueOf(it.key),
                        it.value.multiply(multiplier)
                    )
                }.let {
                    it.toMutableList().apply {
                        add(
                            0,
                            RateItem(currentCurrency, multiplier)
                        )
                    }
                }
            }
        }
}