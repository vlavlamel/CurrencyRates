package com.vlavlamel.currency_rates.api

class RatesApiService(private val networkModule: NetworkModule) {
    val rateApi: RateApi = networkModule.retrofit.create(RateApi::class.java)
}