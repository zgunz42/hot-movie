package com.kangmicin.hotmovie.dagger.components

import com.kangmicin.hotmovie.Application
import com.kangmicin.hotmovie.dagger.builders.ActivityBuilder
import com.kangmicin.hotmovie.dagger.modules.MainModule
import com.kangmicin.hotmovie.dagger.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class,
    MainModule::class,
    NetworkModule::class
])
interface MainComponents: AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<Application>()
}