package com.vlavlamel.currency_rates.api

class RatesApiService(networkModule: NetworkModule) {
    val rateApi: RateApi = networkModule.retrofit.create(RateApi::class.java)
}