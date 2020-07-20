package com.vlavlamel.currency_rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlavlamel.currency_rates.Utils.currencyFormat
import com.vlavlamel.currency_rates.api.NetworkModule
import com.vlavlamel.currency_rates.api.RatesApiService
import com.vlavlamel.currency_rates.model.RateItem
import com.vlavlamel.currency_rates.repo.RatesRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class MainViewModel(private val ratesRepository: RatesRepository) : ViewModel() {
    var multiplier = BigDecimal.ONE
    var currentCurrency = Currency.EUR
    var orderOfRates: List<Currency> = emptyList()

    fun getRates(): Observable<List<RateItem>> =
        Observable.interval(200, 1000, TimeUnit.MILLISECONDS)
            .flatMap {
                ratesRepository.getRates(currentCurrency.code)
                    .observeOn(Schedulers.computation())
                    .map { response ->
                        response.rates.let { map ->
                            if (orderOfRates.isNotEmpty()) {
                                orderOfRates.map {
                                    RateItem(
                                        it,
                                        map[it.code]!!.multiply(multiplier).currencyFormat()
                                    )
                                }
                            } else {
                                map.map {
                                    RateItem(
                                        Currency.valueOf(it.key),
                                        it.value.multiply(multiplier).currencyFormat()
                                    )
                                }
                            }
                        }.let {
                            it.toMutableList().apply {
                                add(
                                    0,
                                    RateItem(currentCurrency, multiplier.currencyFormat())
                                )
                            }
                        }
                    }
            }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(RatesRepository::class.java)
            .newInstance(RatesRepository(RatesApiService(NetworkModule())))

}