package com.kangmicin.hotmovie.dagger.builders

import com.kangmicin.hotmovie.ui.detail.MovieActivity
import com.kangmicin.hotmovie.ui.detail.TvShowActivity
import com.kangmicin.hotmovie.ui.main.MainActivity
import com.kangmicin.hotmovie.ui.main.MainActivityModule
import com.kangmicin.hotmovie.ui.main.movies.MovieModule
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragmentProvider
import com.kangmicin.hotmovie.ui.main.tvs.TvModule
import com.kangmicin.hotmovie.ui.main.tvs.TvsFragmentProvider
import com.kangmicin.hotmovie.ui.setting.SettingsActivity
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

    @ContributesAndroidInjector(modules = [MovieModule::class])
    internal abstract fun contributeMovieActivityModule(): MovieActivity

    @ContributesAndroidInjector(modules = [TvModule::class])
    internal abstract fun contributeTvShowActivityModule(): TvShowActivity

    @ContributesAndroidInjector(modules = [TvModule::class])
    internal abstract fun contributeSettingActivityModule(): SettingsActivity
}