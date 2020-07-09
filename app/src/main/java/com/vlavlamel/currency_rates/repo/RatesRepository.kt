package com.vlavlamel.currency_rates.repo

import com.vlavlamel.currency_rates.Currency
import com.vlavlamel.currency_rates.api.RatesApiService
import com.vlavlamel.currency_rates.api.RatesResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class RatesRepository(private val ratesApiService: RatesApiService) {
    fun getRates(): Observable<RatesResponse> =
        ratesApiService.rateApi.getRates(Currency.EUR.code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}