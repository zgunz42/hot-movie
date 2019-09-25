package com.kangmicin.hotmovie

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kangmicin.hotmovie.model.Movie


class MovieDetailFragment : Fragment() {

//    private lateinit var viewModel: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProviders.of(this).get(Movie::class.java)
//        // TODO: Use the ViewModel
//    }

}
