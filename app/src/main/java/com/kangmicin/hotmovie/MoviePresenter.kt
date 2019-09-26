package com.kangmicin.hotmovie

import android.util.Log
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.Person
import com.kangmicin.hotmovie.model.Rating
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MoviePresenter(view: MovieContract.View, handler: (index: Int) -> Array<String>, size: Int) : MovieContract.ViewPresenter(view) {

    /**
     * An array of sample (dummy) items.
     */
    private val items: MutableList<Movie> = ArrayList()

    /**
     * A map of items, by ID.
     */
    private val itemMap: MutableMap<String, Movie> = HashMap()

    init {
        for (i in 0 until size) {
            addItem(createItem(handler(i), i))
        }
    }

    private fun addItem(item: Movie) {
        items.add(item)
        itemMap[item.id] = item
    }

    private fun extractArgs(string: String): Array<String> {
        Regex("""(?<=\().+(?=\))""").find(string)?.let {
            return it.value.split(',').toTypedArray()
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
        val innerRe = Regex(""".+(?=,\s\()|(?<=,\s).+""")

        Regex("""(?<=\[).+(?=])""").findAll(string).forEach {
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

    private fun getMovieLength(string: String): Int {
        var hours = 0
        var minutes = 0
        Regex("""\d+(?=[hH])""").find(string)?.run {
            hours = this.value.toInt() * 60 * 60
        }

        Regex("""\d+(?=[mM])""").find(string)?.run {
            minutes = this.value.toInt() * 60
        }

        return hours + minutes
    }

    private fun createItem(data: Array<String>, position: Int): Movie {
        val title = data[0]
        val poster = data[1]
        val plot = data[2]
        val genres = data[3].split(',').toList()
        val length = getMovieLength(data[4])
        val director = Person(uuid(), extractArgs(data[5]).first(), null)
        val releaseYear = SimpleDateFormat("MMMM dd, yyyy", Locale.US).parse(data[6])
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

    override fun loadMovies() {
        view.displayMovies(items)
    }
}