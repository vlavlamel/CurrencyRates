package com.vlavlamel.currency_rates

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Utils.dpToPx
import com.vlavlamel.currency_rates.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.currency_adapter.CurrencyRatesAdapter
import com.vlavlamel.currency_rates.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CurrencyRatesAdapter()
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
        adapter.setItems(Currency.values().toList())
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
    }

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    fun <E> MutableList<E>.makeFirst(element: E) {
        remove(element)
        addFirst(element)
    }

    fun <E> MutableList<E>.addFirst(element: E) {
        add(0, element)
    }

    fun RecyclerView.scrollToTop() {
        (this.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 1)
    }
}