package com.kangmicin.hotmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.databinding.CollectionItemBinding
import com.kangmicin.hotmovie.utilities.Helper
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable

abstract class PreloadFragment<T> : DaggerFragment() {
    lateinit var binding: CollectionItemBinding
    private lateinit var mAdapter: ListItemAdapter

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.collection_item, container, false)
        val view = binding.root
        binding.click = Handler()
        initRecyclerView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun initRecyclerView() {
        with(binding.movies) {
            mAdapter = listItemAdapter()
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected val clickListener = View.OnClickListener {
        val intent = startDetail(it.tag as T)
        activity?.startActivity(intent)
    }

    abstract fun startDetail(t: T): Intent

    inner class Handler {

        @Suppress("UNUSED_PARAMETER")
        fun onTapToRetry(view: View) {
            binding.progressBar.visibility = View.VISIBLE
            binding.noInternet.visibility = View.GONE
            getData()
        }
    }
    private fun displaySnackError() {
        val snackbar = Snackbar.make(binding.container, R.string.error_subtitle, Snackbar.LENGTH_LONG)
            .setAction(R.string.tap_to_retry) {Handler()}
        // back bottombar
        snackbar.view.translationY = -1 * resources.getDimension(R.dimen.app_bar_height)
        snackbar.show()
    }

    private fun showNoInternet() {
        binding.movies.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.noInternet.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    fun getData() {
        disposable = Helper.hasInternetConnection().doOnSuccess {
            if (it == false) {
                displaySnackError()
            }
            initData().observe(this, Observer { data ->
                if (data?.isNotEmpty() == true) {
                    binding.progressBar.visibility = View.GONE
                    binding.noInternet.visibility = View.GONE
                    binding.movies.visibility = View.VISIBLE
                    mAdapter.updateAdapter(data)
                } else {
                    showNoInternet()
                }
            })
        }.doOnError {
            showNoInternet()
        }.subscribe()
    }

    abstract fun listItemAdapter(): ListItemAdapter

    abstract fun initData(): LiveData<List<DisplayData>>
}