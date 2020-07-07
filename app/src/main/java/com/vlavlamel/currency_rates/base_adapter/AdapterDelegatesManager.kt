package com.vlavlamel.currency_rates.base_adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.collection.forEach
import androidx.recyclerview.widget.RecyclerView

class AdapterDelegatesManager<T>() {

    companion object {
        const val FALLBACK_DELEGATE_VIEW_TYPE = Int.MAX_VALUE - 1

        private val PAYLOADS_EMPTY_LIST: List<Any> = emptyList()
    }

    protected var delegates = SparseArrayCompat<AdapterDelegate<T>>()
    protected var fallbackDelegate: AdapterDelegate<T>? = null

    constructor(vararg delegates: AdapterDelegate<T>) : this() {
        delegates.forEach {
            addDelegate(it)
        }
    }

    fun addDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> {
        var viewType: Int = delegates.size()
        while (delegates.get(viewType) != null) {
            viewType++
            require(viewType != FALLBACK_DELEGATE_VIEW_TYPE) { "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate." }
        }
        return addDelegate(viewType, false, delegate)
    }

    fun addDelegate(
        viewType: Int,
        delegate: AdapterDelegate<T>
    ): AdapterDelegatesManager<T> = addDelegate(viewType, false, delegate)

    fun addDelegate(
        viewType: Int, allowReplacingDelegate: Boolean,
        delegate: AdapterDelegate<T>
    ): AdapterDelegatesManager<T> = apply {
        require(viewType != FALLBACK_DELEGATE_VIEW_TYPE) {
            ("The view type = "
                    + FALLBACK_DELEGATE_VIEW_TYPE
                    ) + " is reserved for fallback adapter delegate (see setFallbackDelegate() ). Please use another view type."
        }
        require(!(!allowReplacingDelegate && delegates[viewType] != null)) {
            ("An AdapterDelegate is already registered for the viewType = "
                    + viewType
                    + ". Already registered AdapterDelegate is "
                    + delegates[viewType])
        }
        delegates.put(viewType, delegate)
    }

    fun removeDelegate(delegate: AdapterDelegate<T>): AdapterDelegatesManager<T> = apply {
        val indexToRemove = delegates.indexOfValue(delegate)
        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove)
        }
    }

    fun removeDelegate(viewType: Int): AdapterDelegatesManager<T> =
        apply { delegates.remove(viewType) }

    fun getItemViewType(items: T?, position: Int): Int {
        if (items == null) {
            throw NullPointerException("Items datasource is null!")
        }
        delegates.forEach { key, delegate ->
            if (delegate.isForViewType(items, position)) return key
        }
        if (fallbackDelegate != null) {
            return FALLBACK_DELEGATE_VIEW_TYPE
        }
        val errorMessage: String
        errorMessage = if (items is List<*>) {
            val itemString =
                items[position].toString()
            "No AdapterDelegate added that matches item=$itemString at position=$position in data source"
        } else {
            "No AdapterDelegate added for item at position=$position. items=$items"
        }
        throw IllegalStateException(errorMessage)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        (getDelegateForViewType(viewType)
            ?: throw java.lang.NullPointerException("No AdapterDelegate added for ViewType $viewType"))
            .onCreateViewHolder(parent)

    fun onBindViewHolder(
        items: T?, position: Int,
        holder: RecyclerView.ViewHolder, payloads: List<Any>?
    ) {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                "No delegate found for item at position = "
                        + position
                        + " for viewType = "
                        + holder.itemViewType
            )
        delegate.onBindViewHolder(
            items, position, holder,
            payloads ?: PAYLOADS_EMPTY_LIST
        )
    }

    fun onBindViewHolder(
        items: T, position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        onBindViewHolder(items, position, holder, PAYLOADS_EMPTY_LIST)
    }

    fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                "No delegate found for "
                        + holder
                        + " for item at position = "
                        + holder.adapterPosition
                        + " for viewType = "
                        + holder.itemViewType
            )
        delegate.onViewRecycled(holder)
    }

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                "No delegate found for "
                        + holder
                        + " for item at position = "
                        + holder.adapterPosition
                        + " for viewType = "
                        + holder.itemViewType
            )
        return delegate.onFailedToRecycleView(holder)
    }

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                "No delegate found for "
                        + holder
                        + " for item at position = "
                        + holder.adapterPosition
                        + " for viewType = "
                        + holder.itemViewType
            )
        delegate.onViewAttachedToWindow(holder)
    }

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                "No delegate found for "
                        + holder
                        + " for item at position = "
                        + holder.adapterPosition
                        + " for viewType = "
                        + holder.itemViewType
            )
        delegate.onViewDetachedFromWindow(holder)
    }

    fun getViewType(delegate: AdapterDelegate<T>): Int {
        val index = delegates.indexOfValue(delegate)
        return if (index == -1) {
            -1
        } else delegates.keyAt(index)
    }

    fun getDelegateForViewType(viewType: Int): AdapterDelegate<T>? =
        delegates[viewType, fallbackDelegate]
}