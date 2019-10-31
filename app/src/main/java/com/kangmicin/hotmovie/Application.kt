package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.dagger.components.DaggerMainComponents
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMainComponents.builder().create(this)
    }
}