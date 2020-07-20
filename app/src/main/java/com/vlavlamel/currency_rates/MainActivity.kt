package com.vlavlamel.currency_rates

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Utils.currencyFormat
import com.vlavlamel.currency_rates.Utils.dpToPx
import com.vlavlamel.currency_rates.Utils.makeFirst
import com.vlavlamel.currency_rates.Utils.scrollToTop
import com.vlavlamel.currency_rates.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.currency_adapter.CurrencyRatesAdapter
import com.vlavlamel.currency_rates.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter = CurrencyRatesAdapter()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

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
        adapter.adapterEventSubject.subscribe {
            when (it) {
                is AdapterEvent.ClickEvent -> {
                    clearDisposable()
                    mainViewModel.multiplier =
                        adapter.getItems()[it.position].rate.currencyFormat()
                    mainViewModel.currentCurrency = adapter.getItems()[it.position].currency
                    val newList = adapter.getItems().toMutableList()
                    newList.makeFirst(adapter.getItems()[it.position])
                    adapter.setItems(newList) {
                        mainViewModel.orderOfRates = adapter.getItems().map {
                            it.currency
                        }.toMutableList().apply {
                            removeAt(0)
                        }
                        getService()
                        binding.recyclerView.scrollToTop()
                    }
                }
                is AdapterEvent.RateInputEvent -> {
                    mainViewModel.multiplier = it.rate
                    clearDisposable()
                    getService()
                }
            }
        }
        binding.toolbar.title = "Rates"
        getService()
    }

    fun getService() {
        addDisposable(mainViewModel.getRates().map {
            it.let {
                val orderedList = it.toMutableList()
                if (adapter.getItems().isNotEmpty()) {
                    it.forEach {
                        orderedList[adapter.getItems().indexOfFirst { orderItem ->
                            orderItem.currency == it.currency
                        }] = it
                    }
                }
                orderedList
            }
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.setItems(it) })
    }

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}