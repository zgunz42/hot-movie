package com.kangmicin.hotmovie.utilities

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors private constructor(
    private val diskIO: Executor,
    private val mainThread: Executor,
    private val networkIO: Executor){

    companion object {
        @Volatile private var instance: AppExecutors? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance?: AppExecutors(
                    Executors.newSingleThreadExecutor(),
                    Executors.newFixedThreadPool(3),
                    MainThreadExecutor()
                ).also { instance = it }
            }
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    fun networkIO(): Executor {
        return networkIO
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(@NonNull command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}