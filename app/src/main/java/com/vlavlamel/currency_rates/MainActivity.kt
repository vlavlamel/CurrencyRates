package com.vlavlamel.currency_rates

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlavlamel.currency_rates.Utils.dpToPx
import com.vlavlamel.currency_rates.currency_adapter.CurrencyRatesAdapter
import com.vlavlamel.currency_rates.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CurrencyRatesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom =dpToPx(20)
                outRect.top =dpToPx(20)
                outRect.left =dpToPx(20)
                outRect.right =dpToPx(20)
            }
        })
        adapter.items = Currency.values().toList()
        binding.toolbar.title = "Rates"
    }

    override fun inflateViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}