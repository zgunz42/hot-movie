package com.kangmicin.hotmovie.ui.main.tvs

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TvsFragmentProvider {
    @ContributesAndroidInjector(modules = [TvModule::class])
    internal abstract fun contributeTvsProvider(): TvsFragment
}