package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.Person
import com.kangmicin.hotmovie.model.Rating
import com.kangmicin.hotmovie.model.TvShow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Presenter(view: Contract.View,
                handler: (index: Int, type: ModelType) -> Array<String>,
                size: Int) : Contract.ViewPresenter(view) {
    override fun loadTvShows() {
        view.displayTvShows(tvShows)
    }

    /**
     * An array of sample (dummy) movies.
     */
    private val movies: MutableList<Movie> = ArrayList()
    private val tvShows: MutableList<TvShow> = ArrayList()

    /**
     * A map of movies, by ID.
     */
    private val itemMap: MutableMap<String, Movie> = HashMap()

    private val tvShowMap: MutableMap<String, TvShow> = HashMap()

    init {
        for (i in 0 until size) {
            addMovieItem(createMovieItem(handler(i, ModelType.MOVIE), i))
            addTvShowItem(createTvShowItem(handler(i, ModelType.TV_SHOW), i))
        }
    }

    private fun addMovieItem(item: Movie) {
        movies.add(item)
        itemMap[item.id] = item
    }

    private fun addTvShowItem(item: TvShow) {
        tvShows.add(item)
        tvShowMap[item.id] = item
    }

    private fun extractArgs(string: String): Array<String> {
        Regex("""(?<=\().+(?=\))""").find(string)?.let {
            return it.value.split(',').map { i ->  i.trim() }.toTypedArray()
        }

        return emptyArray()
    }

    private fun splitObjects(string: String): Array<Array<String>> {
        var objs = emptyArray<Array<String>>()

        string.split(Regex("""(?<=\)),\s?(?=\()""")).forEach {
            objs = objs.plus(extractArgs(it))
        }

        return objs
    }

    private fun splitMaps(string: String): HashMap<String, Array<String>> {
        val hashMap = HashMap<String, Array<String>>()
        val innerRe = Regex(""".+(?=,\s?\()|(?<=,\s?).+""")

        Regex("""(?<=\[).+(?=])""", RegexOption.UNIX_LINES).findAll(string).forEach {
            val parsed = innerRe.findAll(it.value)
            val key = parsed.elementAt(0).value
            val value = parsed.elementAt(1).value
            hashMap[key] = extractArgs(value)
        }

        return hashMap
    }

    private fun uuid(): String {
        val random = UUID.randomUUID()

        return random.toString()
    }

    private fun parseDuration(string: String): Int {
        var hours = 0
        var minutes = 0

        if (getLocale().language == Locale.US.language) {
            Regex("""\d+(?=[hH])""").find(string)?.run {
                hours = this.value.toInt() * 60 * 60
            }
        }else {
            Regex("""\d+(?=[jJ])""").find(string)?.run {
                hours = this.value.toInt() * 60 * 60
            }
        }

        Regex("""\d+(?=[mM])""").find(string)?.run {
            minutes = this.value.toInt() * 60
        }

        return hours + minutes
    }

    private fun getLocale(): Locale {
        val default = Locale.getDefault()
        val localeId = Locale("in", "ID")

        if (default.language == Locale.US.language
            || default.language == localeId.language) {
            return default
        }

        return Locale.US
    }

    private fun dateLocale(date: String): Date {
        val pattern = "MMMM dd, yyyy"
        return SimpleDateFormat(pattern, getLocale()).parse(date)
    }

    private fun createMovieItem(data: Array<String>, position: Int): Movie {

        val title = data[0]
        val poster = data[1]
        val plot = data[2]
        val genres = data[3].split(',').toList()
        val length = parseDuration(data[4])
        val director = Person(uuid(), extractArgs(data[5]).first(), null)
        val releaseYear = dateLocale(data[6])
        val ratings = splitObjects(data[7]).map { Rating(uuid(), it[0], it[1]) }
        val actors = HashMap<Person, String>()

        splitMaps(data[8]).forEach { (t, u) ->
            val person = Person(uuid(), u[0], u[1])
            actors[person] = t
        }

        return Movie(
            position.toString(),
            title,
            poster,
            plot,
            genres,
            length.toLong(),
            director,
            releaseYear,
            ratings,
            actors
        )
    }

    private fun createTvShowItem(data: Array<String>, position: Int): TvShow {
        val title = data[0]
        val poster = data[1]
        val plot = data[2]
        val genres = data[3].split(',').toList()
        val length = parseDuration(data[4])
        val creators = splitObjects(data[5]).map { Person(uuid(), it[0], null) }
        val release = dateLocale(data[6])
        val ratings = splitObjects(data[7]).map { Rating(uuid(), it[0], it[1]) }
        val actors = HashMap<Person, String>()

        splitMaps(data[8]).forEach { (t, u) ->
            val person = Person(uuid(), u[0], u[1])
            actors[person] = t
        }

        return TvShow(
            position.toString(),
            title,
            poster,
            plot,
            genres,
            length.toLong(),
            creators,
            release,
            ratings,
            actors
        )
    }

    override fun loadMovies() {
        view.displayMovies(movies)
    }

    enum class ModelType {
        MOVIE,
        TV_SHOW
    }
}