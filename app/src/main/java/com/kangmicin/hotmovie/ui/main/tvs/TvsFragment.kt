package com.kangmicin.hotmovie.ui.main.tvs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.ui.detail.TvShowActivity
import com.kangmicin.hotmovie.ui.main.DataItemFragment
import javax.inject.Inject

open class TvsFragment : DataItemFragment<Tv>() {
    override fun startDetail(t: Tv): Intent {
        val intent = Intent(this.activity, TvShowActivity::class.java)
        intent.putExtra(TvShowActivity.TV_SHOW_KEY, t.id)
        return intent
    }

    override fun initData(): LiveData<List<DisplayData>> {
        return Transformations.map(viewModel.getTvs()){ it as List<DisplayData> }
    }

    companion object {
        fun newInstance() = TvsFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: TvsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run {
            viewModel = ViewModelProviders.of(this, factory).get(TvsViewModel::class.java)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}