package com.vlavlamel.currency_rates

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Utils.dpToPx
import com.vlavlamel.currency_rates.Utils.makeFirst
import com.vlavlamel.currency_rates.Utils.scrollToTop
import com.vlavlamel.currency_rates.api.NetworkModule
import com.vlavlamel.currency_rates.api.RatesApiService
import com.vlavlamel.currency_rates.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.currency_adapter.CurrencyRatesAdapter
import com.vlavlamel.currency_rates.databinding.ActivityMainBinding
import com.vlavlamel.currency_rates.repo.RatesRepository

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val adapter = CurrencyRatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = dpToPx(20)
                outRect.top = dpToPx(20)
                outRect.left = dpToPx(20)
                outRect.right = dpToPx(20)
            }
        })
        addDisposable(adapter.adapterEventSubject.subscribe {
            when (it) {
                is AdapterEvent.ClickEvent -> {
                    val newList = adapter.getItems().toMutableList()
                    newList.makeFirst(adapter.getItems()[it.position])
                    adapter.setItems(newList) {
                        binding.recyclerView.scrollToTop()
                    }
                }
                is AdapterEvent.RateInputEvent -> {
                    println("TEST ${it.rate}")
                }
            }
        })
        binding.toolbar.title = "Rates"
        getService()
    }

    fun getService() {
        val mainViewModel = MainViewModel(RatesRepository(RatesApiService(NetworkModule())))
        addDisposable(mainViewModel.getRates().subscribe {
            adapter.setItems(it)
        })
    }

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}