package com.vlavlamel.currency_rates.presentation.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.Utils.currencyFormat
import com.vlavlamel.currency_rates.api.NetworkModule
import com.vlavlamel.currency_rates.api.RatesApiService
import com.vlavlamel.currency_rates.presentation.rates.currency_adapter.item.RateItem
import com.vlavlamel.currency_rates.repo.RatesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

const val INITIAL_DELAY = 200L
const val PERIOD = 1000L

class RatesViewModel(private val ratesRepository: RatesRepository) : ViewModel() {
    var multiplier: BigDecimal = BigDecimal.ONE
    private var currentCurrency = Currency.getCurrency("EUR")
    var orderOfRates: List<Currency> = emptyList()

    fun setCurrentOrderOfRates(items: List<RateItem>) {
        items[0].apply {
            multiplier = rate.currencyFormat()
            currentCurrency = currency
        }
        orderOfRates = items.map { it.currency }
    }

    fun getRates(): Observable<List<RateItem>> =
        Observable.interval(INITIAL_DELAY, PERIOD, TimeUnit.MILLISECONDS)
            .flatMap {
                ratesRepository.getRates(currentCurrency.code)
                    .observeOn(Schedulers.computation())
                    .map { response ->
                        response.rates.let { ratesMap ->
                            mutableListOf<RateItem>().toMutableList().apply {
                                add(RateItem(currentCurrency, multiplier.currencyFormat()))
                                if (orderOfRates.isNotEmpty()) {
                                    orderOfRates.mapNotNullTo(this) { currency ->
                                        ratesMap[currency.code]?.let {
                                            RateItem(
                                                currency,
                                                it.multiply(multiplier).currencyFormat()
                                            )
                                        }
                                    }
                                } else {
                                    ratesMap.mapTo(this) {
                                        RateItem(
                                            Currency.getCurrency(it.key),
                                            it.value.multiply(multiplier).currencyFormat()
                                        )
                                    }
                                }
                            }
                        } as List<RateItem>
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
}

class RatesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(RatesRepository::class.java)
            .newInstance(RatesRepository(RatesApiService(NetworkModule())))
}