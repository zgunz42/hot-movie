package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.model.Movie
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieContentUnitTest {
    @Test
    fun load_all_movies_isAll() {
        val view = MockView {
            val first: Movie = it.first()
            Assert.assertEquals(first.title, "Bird Box")
            Assert.assertEquals(first.poster, "@drawable/poster_birdbox")
            Assert.assertEquals(first.genre.size, 5)
            Assert.assertEquals(first.rating.size, 3)
            Assert.assertEquals(first.length, (2*60*60) + (4*60))
            Assert.assertEquals(first.actors.entries.size, 5)
        }

        val presenter = MoviePresenter(view, { stub() }, 1)

        presenter.loadMovies()

    }

    private fun stub(): Array<String> {
        return arrayOf(
            "Bird Box",
            "@drawable/poster_birdbox",
            """When a mysterious force decimates the population, only one thing is certain — if you see it, you die. The
            survivors must now avoid coming face to face with an entity that takes the form of their worst fears.
                Searching for hope and a new beginning, a woman and her children embark on a dangerous journey through the
            woods and down a river to find the one place that may offer sanctuary. To make it, they'll have to cover
            their eyes from the evil that chases them — and complete the trip blindfolded.""",
            "drama, thirller, horror, Doomsday, Science fiction",
            "2h 4m",
            "(Susanne Bier)",
            "December 21, 2018",
            "(6.6/10, IMDb), (63%, Rotten Tomatoes), (51%, Metacritic)",
            """
                [Malorie Hayes, (Sandra Bullock, @drawable/sandra_bullock)],
                [Tom, (Trevante Rhodes, @drawable/trevante_rhodes)],
                [Douglas, (John Malkovich, @drawable/john_malkovich)],
                [Jessica, (Sarah Paulson, @drawable/sarah_paulson)],
                [Cheryl, (Jacki Weaver, @drawable/jacki_weaver)],
            """
        )
    }

    inner class MockView(private val validator: (movies: List<Movie>) -> Unit): MovieContract.View {
        override fun displayMovies(movies: List<Movie>) {
            validator(movies)
        }
    }
}
