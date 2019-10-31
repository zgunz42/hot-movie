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
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.databinding.CollectionItemBinding
import com.kangmicin.hotmovie.ui.detail.DetailActivity
import com.kangmicin.hotmovie.utilities.Helper
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable

abstract class DataItemFragment : DaggerFragment() {
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
            mAdapter = ListItemAdapter(ArrayList(), clickListener)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private val clickListener = View.OnClickListener {
        val movie = it.tag as Movie
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", movie.id)
        //TODO: Enum and Dynamic
        intent.putExtra("type", "movie")
        activity?.startActivity(intent)
    }

    inner class Handler {
        @Suppress("UNUSED_PARAMETER")
        fun onTapToRetry(view: View) {
            binding.progressBar.visibility = View.VISIBLE
            binding.noInternet.visibility = View.GONE
            getData()
        }
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

    private fun displaySnackError() {
        Snackbar.make(binding.container, R.string.error_subtitle, Snackbar.LENGTH_LONG)
            .setAction(R.string.tap_to_retry) {Handler()}
            .show()
    }

    abstract fun initData(): LiveData<List<DisplayData>>

    private fun showNoInternet() {
        binding.movies.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.noInternet.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}