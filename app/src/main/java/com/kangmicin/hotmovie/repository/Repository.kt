package com.kangmicin.hotmovie.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver

abstract class Repository<T, S>(
    private val executors: AppExecutors
) {

    private val mItems = MutableLiveData<List<T>>()

    fun getItems(): LiveData<List<T>> {
        doFetchSourceItems()
        return mItems
    }

    fun addItems(items:List<T>) {
        Log.i("favorite", "update $items")
        mItems.postValue(items)
    }

    fun getItem(id: Long): LiveData<T> {
        // do prefetch
        var preftech = false
        return Transformations.switchMap(mItems){
            val target = MediatorLiveData<T>()
            if (it != null) {
                val item = getItemId(id, it)
                if (item != null) {
                    if (!preftech) {
                        doFetchSourceItem(item)
                        preftech = true
                    }
                    target.value = item
                }
            }

            return@switchMap target
        }
    }

    private fun doFetchSourceItem(item: T) {
        executors.networkIO().execute {
            val observer = object : DisposableSingleObserver<T>() {
                override fun onSuccess(t: T) {
                    doDataFetched(listOf(item))
                    doFetchLocale()
                }

                override fun onError(e: Throwable) {
                    doFetchLocale()
                }

            }
            fetchItemDetailSource(item, observer)
        }
    }

    private fun doFetchSourceItems() {
        executors.networkIO().execute {
            val observer = object : DisposableSingleObserver<S>() {
                override fun onSuccess(t: S) {
                    val target = fromSource(t)
                    doDataFetched(target)
                    doFetchLocale()
                }

                override fun onError(e: Throwable) {
                    doFetchLocale()
                }

            }
            fetchItemSource(observer)
        }
    }

    fun doDataFetched(target: List<T>) {
        executors.diskIO().execute {
            onDataFetched(target)
        }
    }

    fun doFetchLocale() {
        executors.diskIO().execute {
            val items = fetchLocale()

            if (items.isNotEmpty()) {
                addItems(items)
            }else {
                doFetchSourceItems()
            }
        }
    }

    fun updateItem(value: T) {
        executors.diskIO().execute {
            Log.i("favorite", "update $value")
            updateDBItem(value)
            doFetchLocale()
        }
    }

    abstract fun getItemId(id: Long, data: List<T>): T?

    abstract fun fetchLocale(): List<T>

    abstract fun fetchItemSource(observer: DisposableSingleObserver<S>)

    abstract fun onDataFetched(target: List<T>)

    abstract fun fromSource(to: S): MutableList<T>

    abstract fun fromDetail(from: T, to: Any): T

    abstract fun fetchItemDetailSource(item: T, observer: DisposableSingleObserver<T>)

    abstract fun updateDBItem(item: T)
}