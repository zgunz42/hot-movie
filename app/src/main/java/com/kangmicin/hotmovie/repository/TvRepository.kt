package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.data.core.TvDAO
import com.kangmicin.hotmovie.data.entity.Person
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.network.ImageSize
import com.kangmicin.hotmovie.network.WebClient
import com.kangmicin.hotmovie.network.poko.Credits
import com.kangmicin.hotmovie.network.poko.DiscoverTv
import com.kangmicin.hotmovie.network.poko.TvDetail
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class TvRepository @Inject constructor(
    executors: AppExecutors,
    private val dao: TvDAO,
    private val webClient: WebClient
): Repository<Tv, DiscoverTv>(executors) {
    override fun updateDBItem(item: Tv) {
        dao.updateTvs(item)
    }

    override fun getItemId(id: Long, data: List<Tv>): Tv? {
        var tv: Tv? = null
        data.forEach {
            if (it.id == id) {
                tv = it
            }
        }

        return tv
    }

    override fun fetchLocale() = dao.getAll()

    override fun fetchItemSource(observer: DisposableSingleObserver<DiscoverTv>) {
        webClient.getDiscoverTvFromServer(observer)
    }

    override fun fromDetail(from: Tv, to: Any): Tv {
        if (to is TvDetail) {
            from.length = to.episodeRunTime.first() + 0L
            to.genres.forEach { m ->
                from.genre = from.genre + m.name
            }
        }
        if (to is Credits) {
            from.actors = emptyMap()
            from.creators = emptyList()
            to.crew.forEach {
                if (it.job == "Director") {
                    val director = Person(it.id + 0L, it.name, null)
                    from.creators = from.creators + director
                }
            }
            val pair = to.cast.map {
                val profileUrl = webClient.getImageUrl(
                    it.profilePath ?: "",
                    ImageSize.Small
                )
                val actor = Person(it.id + 0L, it.name, profileUrl, it.order)

                Pair(it.character, actor)
            }

            from.actors = mapOf(*pair.toTypedArray())
        }

        return from
    }


    override fun fetchItemDetailSource(item: Tv, observer: DisposableSingleObserver<Tv>){

        webClient.getTvDetailFromServer("${item.id}", object : DisposableSingleObserver<TvDetail>() {
            override fun onError(e: Throwable) {observer.onError(e)}

            override fun onSuccess(t: TvDetail) {
                observer.onSuccess(fromDetail(item, t))
            }
        })
        webClient.getTvCrewFromServer("${item.id}", object : DisposableSingleObserver<Credits>() {
            override fun onError(e: Throwable) {observer.onError(e)}

            override fun onSuccess(t: Credits) {
                observer.onSuccess(fromDetail(item, t))
            }
        })
    }

    override fun onDataFetched(target: List<Tv>) {
        dao.insertTvs(*target.toTypedArray())
    }

    override fun fromSource(to: DiscoverTv): MutableList<Tv> {
        return webClient.convertToTvShowList(to)
    }
}