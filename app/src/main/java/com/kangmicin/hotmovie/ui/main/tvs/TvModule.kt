package com.kangmicin.hotmovie.ui.main.tvs

import androidx.lifecycle.ViewModelProvider
import com.kangmicin.hotmovie.repository.TvRepository
import com.kangmicin.hotmovie.utilities.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class TvModule {
    @Provides
    internal fun provideViewModel(repository: TvRepository): TvsViewModel {
        return TvsViewModel(repository)
    }

    @Provides
    internal fun provideViewModelFactory(viewModel: TvsViewModel): ViewModelProvider.Factory{
        return ViewModelProviderFactory(viewModel)
    }
}