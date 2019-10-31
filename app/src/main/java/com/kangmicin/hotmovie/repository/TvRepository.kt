package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.data.core.TvDAO
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.network.WebClient
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import com.kangmicin.hotmovie.network.poko.TvDetail
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class TvRepository @Inject constructor(
    executors: AppExecutors,
    private val dao: TvDAO,
    private val webClient: WebClient
): Repository<Tv, DiscoverTv, TvDetail>(executors) {
    override fun fetchLocale() = dao.getAll()

    override fun fetchItemSource(observer: DisposableSingleObserver<DiscoverTv>) {
        webClient.getDiscoverTvFromServer(observer)
    }

    override fun onDataFetched(target: List<Tv>) {
        dao.insertTvs(*target.toTypedArray())
    }

    override fun fromSource(to: DiscoverTv): MutableList<Tv> {
        return webClient.convertToTvShowList(to)
    }
    override fun fromDetail(from: Tv, to: TvDetail) {}
}