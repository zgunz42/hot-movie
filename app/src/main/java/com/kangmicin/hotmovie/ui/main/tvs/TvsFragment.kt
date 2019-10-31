package com.kangmicin.hotmovie.ui.main.tvs

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
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragment
import javax.inject.Inject

class TvsFragment : DataItemFragment() {
    override fun initData(): LiveData<List<DisplayData>> {
        return Transformations.map(viewModel.getTvs()){ it as List<DisplayData> }
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: TvsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, factory).get(TvsViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}