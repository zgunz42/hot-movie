package com.kangmicin.hotmovie.utilities

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import java.util.concurrent.Executor


class AppExecutors constructor(
    private val diskIO: Executor,
    private val mainThread: Executor,
    private val networkIO: Executor){

    fun mainThread(): Executor {
        return mainThread
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(@NonNull command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}