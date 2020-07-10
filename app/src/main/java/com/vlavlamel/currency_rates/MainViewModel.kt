package com.vlavlamel.currency_rates

import com.vlavlamel.currency_rates.model.RateItem
import com.vlavlamel.currency_rates.repo.RatesRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit

class MainViewModel(private val ratesRepository: RatesRepository) {
    var multiplier = BigDecimal.ONE
    var currentCurrency = Currency.EUR
    var orderOfRates: List<Currency> = emptyList()

    fun getRates(): Observable<List<RateItem>> =
        Observable.interval(500, 1000, TimeUnit.MILLISECONDS)
            .flatMap {
                ratesRepository.getRates(currentCurrency.code)
                    .observeOn(Schedulers.computation())
                    .map { response ->
                        response.rates.let { map ->
                            if (orderOfRates.isNotEmpty()) {
                                orderOfRates.map {
                                    RateItem(
                                        it,
                                        map[it.code]!!.multiply(multiplier)
                                            .setScale(2, RoundingMode.CEILING)
                                    )
                                }
                            } else {
                                map.map {
                                    RateItem(
                                        Currency.valueOf(it.key),
                                        it.value.multiply(multiplier)
                                    )
                                }
                            }
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