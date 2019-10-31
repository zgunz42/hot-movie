package com.kangmicin.hotmovie.ui.main.movies

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MoviesFragmentProvider {

    @ContributesAndroidInjector(modules = [MovieModule::class])
    internal abstract fun contributeMoviesProvider(): MoviesFragment
}