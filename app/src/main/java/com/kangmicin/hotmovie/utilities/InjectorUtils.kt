package com.kangmicin.hotmovie.utilities

import com.kangmicin.hotmovie.repository.MovieRepository
import com.kangmicin.hotmovie.repository.TvRepository
import com.kangmicin.hotmovie.storage.CacheStorage
import com.kangmicin.hotmovie.ui.main.MoviesViewModelFactory
import com.kangmicin.hotmovie.ui.main.TvViewModelFactory

object InjectorUtils {
    fun provideMoviesViewModelFactory(): MoviesViewModelFactory {
        val storage = CacheStorage.getInstance()
        val appExecutors = AppExecutors.getInstance()
        val movieRepository = MovieRepository.getInstance(appExecutors, storage.movieDao)
        return MoviesViewModelFactory(movieRepository)
    }

    fun provideTvViewModelFactory(): TvViewModelFactory {
        val storage = CacheStorage.getInstance()
        val appExecutors = AppExecutors.getInstance()
        val movieRepository = TvRepository.getInstance(appExecutors, storage.tvDao)
        return TvViewModelFactory(movieRepository)
    }
}