package com.vlavlamel.currency_rates

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/*
* country flags - https://www.flaticon.com/packs/countrys-flags
* */

data class Currency(val code: String, @StringRes val fullName: Int, @DrawableRes val image: Int) {

    companion object {
        fun getCurrency(code: String): Currency = when (code) {
            "AUD" -> Currency(code, R.string.aud_currency, R.drawable.ic_australia)
            "BGN" -> Currency(code, R.string.bgn_currency, R.drawable.ic_bulgaria)
            "BRL" -> Currency(code, R.string.brl_currency, R.drawable.ic_brazil)
            "CAD" -> Currency(code, R.string.cad_currency, R.drawable.ic_canada)
            "CHF" -> Currency(code, R.string.chf_currency, R.drawable.ic_switzerland)
            "CNY" -> Currency(code, R.string.cny_currency, R.drawable.ic_china)
            "CZK" -> Currency(code, R.string.czk_currency, R.drawable.ic_czech_republic)
            "DKK" -> Currency(code, R.string.dkk_currency, R.drawable.ic_denmark)
            "EUR" -> Currency(code, R.string.eur_currency, R.drawable.ic_european_union)
            "GBP" -> Currency(code, R.string.gbp_currency, R.drawable.ic_united_kingdom)
            "HKD" -> Currency(code, R.string.hkd_currency, R.drawable.ic_hong_kong)
            "HRK" -> Currency(code, R.string.hrk_currency, R.drawable.ic_croatia)
            "HUF" -> Currency(code, R.string.huf_currency, R.drawable.ic_hungary)
            "IDR" -> Currency(code, R.string.idr_currency, R.drawable.ic_indonesia)
            "ILS" -> Currency(code, R.string.ils_currency, R.drawable.ic_israel)
            "INR" -> Currency(code, R.string.inr_currency, R.drawable.ic_india)
            "ISK" -> Currency(code, R.string.isk_currency, R.drawable.ic_iceland)
            "JPY" -> Currency(code, R.string.jpy_currency, R.drawable.ic_japan)
            "KRW" -> Currency(code, R.string.krw_currency, R.drawable.ic_south_korea)
            "MXN" -> Currency(code, R.string.mxn_currency, R.drawable.ic_mexico)
            "MYR" -> Currency(code, R.string.myr_currency, R.drawable.ic_malaysia)
            "NOK" -> Currency(code, R.string.nok_currency, R.drawable.ic_norway)
            "NZD" -> Currency(code, R.string.nzd_currency, R.drawable.ic_new_zealand)
            "PHP" -> Currency(code, R.string.php_currency, R.drawable.ic_philippines)
            "PLN" -> Currency(code, R.string.pln_currency, R.drawable.ic_poland)
            "RON" -> Currency(code, R.string.ron_currency, R.drawable.ic_romania)
            "RUB" -> Currency(code, R.string.rub_currency, R.drawable.ic_russia)
            "SEK" -> Currency(code, R.string.sek_currency, R.drawable.ic_sweden)
            "SGD" -> Currency(code, R.string.sgd_currency, R.drawable.ic_singapore)
            "THB" -> Currency(code, R.string.thb_currency, R.drawable.ic_thailand)
            "TRY" -> Currency(code, R.string.try_currency, R.drawable.ic_turkey)
            "USD" -> Currency(code, R.string.usd_currency, R.drawable.ic_united_states_of_america)
            "ZAR" -> Currency(code, R.string.zar_currency, R.drawable.ic_south_africa)
            else -> Currency(code, R.string.default_currency, R.drawable.ic_default_currency)
        }
    }
}