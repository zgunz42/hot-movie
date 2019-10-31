package com.kangmicin.hotmovie.dagger.modules

import android.content.Context
import androidx.room.Room
import com.kangmicin.hotmovie.Application
import com.kangmicin.hotmovie.data.core.AppDatabase
import com.kangmicin.hotmovie.data.core.MovieDAO
import com.kangmicin.hotmovie.data.core.TvDAO
import com.kangmicin.hotmovie.network.WebClient
import com.kangmicin.hotmovie.repository.MovieRepository
import com.kangmicin.hotmovie.repository.TvRepository
import com.kangmicin.hotmovie.utilities.AppExecutors
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    internal fun provideExecutors(): AppExecutors {
        return AppExecutors(
            Executors.newSingleThreadExecutor(),
            AppExecutors.MainThreadExecutor(),
            Executors.newFixedThreadPool(3))
    }

    @Singleton
    @Provides
    internal fun provideMovieRepository(executors: AppExecutors, dao: MovieDAO, webClient: WebClient): MovieRepository {
        return MovieRepository(executors, dao, webClient)
    }

    @Singleton
    @Provides
    internal fun provideTvRepository(executors: AppExecutors, dao: TvDAO, webClient: WebClient): TvRepository {
        return TvRepository(executors, dao, webClient)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "top_movies_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase):
            MovieDAO = appDatabase.movieDAO()

    @Provides
    @Singleton
    fun provideTvDao(appDatabase: AppDatabase):
            TvDAO = appDatabase.tvDAO()
}