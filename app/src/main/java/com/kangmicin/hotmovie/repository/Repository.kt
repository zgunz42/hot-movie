package com.kangmicin.hotmovie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver

abstract class Repository<T, S, D>(
    private val executors: AppExecutors
) {

    private val mItems = MutableLiveData<List<T>>()

    fun getItems(): LiveData<List<T>> {
        doFetchSourceItems()
        return mItems
    }

    fun addItems(items:List<T>) {
        mItems.postValue(items)
    }

//    fun clearItems() {
//        mItemList.clear()
//    }

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
//                clearItems()
                addItems(items)
            }else {
                doFetchSourceItems()
            }
        }
    }

    abstract fun fetchLocale(): List<T>

    abstract fun fetchItemSource(observer: DisposableSingleObserver<S>)

    abstract fun onDataFetched(target: List<T>)

    abstract fun fromSource(to: S): MutableList<T>

    abstract fun fromDetail(from: T, to: D)
}