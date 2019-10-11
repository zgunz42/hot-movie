package com.kangmicin.hotmovie.storage

class CacheStorage {

    val movieDao = MovieDao()
    val tvDao = TvShowDao()

    companion object {
        @Volatile private var instance: CacheStorage? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: CacheStorage().also { instance = it }
            }
    }
}