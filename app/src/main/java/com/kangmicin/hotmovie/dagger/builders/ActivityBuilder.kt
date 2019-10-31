package com.kangmicin.hotmovie.dagger.builders

import com.kangmicin.hotmovie.ui.main.MainActivity
import com.kangmicin.hotmovie.ui.main.MainActivityModule
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragmentProvider
import com.kangmicin.hotmovie.ui.main.tvs.TvsFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        MoviesFragmentProvider::class,
        TvsFragmentProvider::class
    ])
    internal abstract fun contributeActivityModule(): MainActivity
}