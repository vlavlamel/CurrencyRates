package com.vlavlamel.currency_rates.presentation.rates

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Utils.dpToPx
import com.vlavlamel.currency_rates.Utils.makeFirst
import com.vlavlamel.currency_rates.Utils.scrollToTop
import com.vlavlamel.currency_rates.databinding.ActivityMainBinding
import com.vlavlamel.currency_rates.presentation.base.BaseActivity
import com.vlavlamel.currency_rates.presentation.base.base_adapter.AdapterEvent
import com.vlavlamel.currency_rates.presentation.rates.currency_adapter.CurrencyRatesAdapter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class RatesActivity : BaseActivity<ActivityMainBinding>() {

    private val ratesAdapter = CurrencyRatesAdapter()
    private val mainViewModel: RatesViewModel by viewModels {
        RatesViewModelFactory()
    }
    private val viewModelCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecycler()
        subscribeToAdapter()
        binding.toolbar.title = "Rates"
    }

    private fun setupRecycler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ratesAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
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
        }
    }

    private fun subscribeToAdapter() {
        addDisposable(ratesAdapter.adapterEventSubject.subscribe {
            clearViewModelDisposable()
            when (it) {
                is AdapterEvent.ClickEvent -> {
                    val newList = ratesAdapter.getItems().toMutableList()
                    newList.makeFirst(ratesAdapter.getItems()[it.position])
                    ratesAdapter.setItems(newList) {
                        mainViewModel.setCurrentOrderOfRates(ratesAdapter.getItems())
                        binding.recyclerView.scrollToTop()
                        subscribeToViewModel()
                    }
                }
                is AdapterEvent.RateInputEvent -> {
                    mainViewModel.multiplier = it.rate
                    subscribeToViewModel()
                }
            }
        })
    }

    private fun subscribeToViewModel() {
        addViewModelDisposable(
            mainViewModel.getRates().subscribe({ ratesAdapter.setItems(it) },
                { Toast.makeText(this, "Some error has occurred", Toast.LENGTH_SHORT).show() })
        )
    }

    override fun onResume() {
        super.onResume()
        subscribeToViewModel()
    }

    override fun onPause() {
        super.onPause()
        clearViewModelDisposable()
    }

    private fun addViewModelDisposable(disposable: Disposable) =
        viewModelCompositeDisposable.add(disposable)

    private fun clearViewModelDisposable() = viewModelCompositeDisposable.clear()

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}