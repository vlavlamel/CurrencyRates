package com.vlavlamel.currency_rates.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RateApi {
    @GET("api/android/latest")
    fun getRates(@Query("base") currencyCode: String): Observable<RatesResponse>
}