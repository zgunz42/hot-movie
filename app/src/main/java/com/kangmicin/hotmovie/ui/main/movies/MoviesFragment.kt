package com.kangmicin.hotmovie.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.ui.main.DataItemFragment
import javax.inject.Inject

class MoviesFragment: DataItemFragment() {
    override fun initData(): LiveData<List<DisplayData>>{
        return Transformations.map(viewModel.getMovies()){ it as List<DisplayData> }
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}