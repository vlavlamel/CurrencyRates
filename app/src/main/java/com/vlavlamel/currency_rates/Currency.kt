package com.vlavlamel.currency_rates

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/*
* country flags - https://www.flaticon.com/packs/countrys-flags
* */

enum class Currency(val code: String, @StringRes val fullName: Int, @DrawableRes val image: Int) {
    AUD("AUD", R.string.aud_currency, R.drawable.ic_australia),
    BGN("BGN", R.string.bgn_currency, R.drawable.ic_bulgaria),
    BRL("BRL", R.string.brl_currency, R.drawable.ic_brazil),
    CAD("CAD", R.string.cad_currency, R.drawable.ic_canada),
    CHF("CHF", R.string.chf_currency, R.drawable.ic_switzerland),
    CNY("CNY", R.string.cny_currency, R.drawable.ic_china),
    CZK("CZK", R.string.czk_currency, R.drawable.ic_czech_republic),
    DKK("DKK", R.string.dkk_currency, R.drawable.ic_denmark),
    EUR("EUR", R.string.eur_currency, R.drawable.ic_european_union),
    GBP("GBP", R.string.gbp_currency, R.drawable.ic_united_kingdom),
    HKD("HKD", R.string.hkd_currency, R.drawable.ic_hong_kong),
    HRK("HRK", R.string.hrk_currency, R.drawable.ic_croatia),
    HUF("HUF", R.string.huf_currency, R.drawable.ic_hungary),
    IDR("IDR", R.string.idr_currency, R.drawable.ic_indonesia),
    ILS("ILS", R.string.ils_currency, R.drawable.ic_israel),
    INR("INR", R.string.inr_currency, R.drawable.ic_india),
    ISK("ISK", R.string.isk_currency, R.drawable.ic_iceland),
    JPY("JPY", R.string.jpy_currency, R.drawable.ic_japan),
    KRW("KRW", R.string.krw_currency, R.drawable.ic_south_korea),
    MXN("MXN", R.string.mxn_currency, R.drawable.ic_mexico),
    MYR("MYR", R.string.myr_currency, R.drawable.ic_malaysia),
    NOK("NOK", R.string.nok_currency, R.drawable.ic_norway),
    NZD("NZD", R.string.nzd_currency, R.drawable.ic_new_zealand),
    PHP("PHP", R.string.php_currency, R.drawable.ic_philippines),
    PLN("PLN", R.string.pln_currency, R.drawable.ic_poland),
    RON("RON", R.string.ron_currency, R.drawable.ic_romania),
    RUB("RUB", R.string.rub_currency, R.drawable.ic_russia),
    SEK("SEK", R.string.sek_currency, R.drawable.ic_sweden),
    SGD("SGD", R.string.sgd_currency, R.drawable.ic_singapore),
    THB("THB", R.string.thb_currency, R.drawable.ic_thailand),
    TRY("TRY", R.string.try_currency, R.drawable.ic_turkey),
    USD("USD", R.string.usd_currency, R.drawable.ic_united_states_of_america),
    ZAR("ZAR", R.string.zar_currency, R.drawable.ic_south_africa)
}