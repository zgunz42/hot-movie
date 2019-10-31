package com.kangmicin.hotmovie.ui.main.movies

import androidx.lifecycle.ViewModelProvider
import com.kangmicin.hotmovie.repository.MovieRepository
import com.kangmicin.hotmovie.utilities.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @Provides
    internal fun provideViewModel(repository: MovieRepository): MoviesViewModel{
        return MoviesViewModel(repository)
    }

    @Provides
    internal fun provideViewModelFactory(viewModel: MoviesViewModel): ViewModelProvider.Factory{
        return ViewModelProviderFactory(viewModel)
    }
}