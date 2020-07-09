package com.vlavlamel.currency_rates

import com.vlavlamel.currency_rates.model.RateItem
import com.vlavlamel.currency_rates.repo.RatesRepository
import io.reactivex.rxjava3.core.Observable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class MainViewModel(private val ratesRepository: RatesRepository) {
    fun getRates(): Observable<List<RateItem>> = Observable.interval(1, TimeUnit.SECONDS)
        .flatMap {
            ratesRepository.getRates().map { response ->
                response.rates.map {
                    RateItem(Currency.valueOf(it.key), it.value)
                }.let {
                    it.toMutableList().apply {
                        add(0, RateItem(Currency.EUR, BigDecimal.ONE))
                    }
                }
            }
        }
}