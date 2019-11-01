package com.kangmicin.hotmovie.repository

import com.kangmicin.hotmovie.data.core.MovieDAO
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.data.entity.Person
import com.kangmicin.hotmovie.network.ImageSize
import com.kangmicin.hotmovie.network.WebClient
import com.kangmicin.hotmovie.network.poko.Credits
import com.kangmicin.hotmovie.network.poko.DiscoverMovie
import com.kangmicin.hotmovie.network.poko.MovieDetail
import com.kangmicin.hotmovie.utilities.AppExecutors
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MovieRepository @Inject constructor(
    executors: AppExecutors,
    private val dao: MovieDAO,
    private val webClient: WebClient
): Repository<Movie, DiscoverMovie>(executors){

    override fun getItemId(id: Long, data: List<Movie>): Movie? {
        var movie: Movie? = null
        data.forEach {
            if (it.id == id) {
                movie = it
            }
        }

        return movie
    }

    override fun fetchLocale() = dao.getAll()

    override fun fetchItemSource(observer: DisposableSingleObserver<DiscoverMovie>) {
        webClient.getDiscoverMovieFromServer(observer)
    }

     override fun fetchItemDetailSource(item: Movie, observer: DisposableSingleObserver<Movie>){

        webClient.getMovieDetailFromServer("${item.id}", object : DisposableSingleObserver<MovieDetail>() {
            override fun onError(e: Throwable) {observer.onError(e)}

            override fun onSuccess(t: MovieDetail) {
                observer.onSuccess(fromDetail(item, t))
            }
        })
        webClient.getMovieCrewFromServer("${item.id}", object : DisposableSingleObserver<Credits>() {
            override fun onError(e: Throwable) {observer.onError(e)}

            override fun onSuccess(t: Credits) {
                observer.onSuccess(fromDetail(item, t))
            }
        })
    }

    override fun onDataFetched(target: List<Movie>) {
        dao.insertMovies(*target.toTypedArray())
    }

    override fun fromSource(to: DiscoverMovie): MutableList<Movie> {
        return webClient.convertToMovieList(to)
    }

    override fun fromDetail(from: Movie, to: Any): Movie {
        if (to is MovieDetail) {
            from.length = to.runtime + 0L
            to.genres.forEach { m ->
                from.genre = from.genre + m.name
            }
        }
        if (to is Credits) {
            from.actors = emptyMap()
            from.directors = emptyList()
            to.crew.forEach {
                if (it.job == "Director") {
                    val director = Person(it.id + 0L, it.name, null)
                    from.directors = from.directors + director
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
}