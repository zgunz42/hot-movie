package com.kangmicin.hotmovie

import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ContentLoadUnitTest {
    @Test
    fun load_all_movies_isAll() {
        val mockView = mock<Contract.View>()
        val presenter = Presenter(mockView, { _, _ -> stubMovie() }, 1)

        presenter.loadMovies()

        verify(mockView).displayMovies(argThat {
            val first: Movie = first()
            first.title == "Bird Box" &&
                    first.poster == "@drawable/poster_birdbox" &&
                    first.genre.size == 5 &&
                    first.rating.size == 3 &&
                    first.length == (2L*60*60) + (4*60) &&
                    first.actors.entries.size == 5
        })

    }

    @Test
    fun load_all_tv_show_isAll() {
        val mockView = mock<Contract.View>()
        val presenter = Presenter(mockView, { _, _ -> stubTvShow() }, 1)
        presenter.loadTvShows()

        verify(mockView).displayTvShows(argThat {
            val first: Tv = first()
            first.title == "The Simpson" &&
                    first.poster == "@drawable/poster_the_simpson" &&
                    first.genre.size == 2 &&
                    first.rating.size == 3 &&
                    first.length == 22*60L &&
                    first.actors.entries.size == 5
        })
    }

    private fun stubMovie(): Array<String> {
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
    private fun stubTvShow(): Array<String> {
        return arrayOf(
            "The Simpson",
            "@drawable/poster_the_simpson",
            """
                Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of
        the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the
        beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The
        show has also made name for itself in its fearless satirical take on politics, media and American life in
        general""",
        "ANIMATION, COMEDY",
        "22m",
        "(Matt Groening)",
        "December 17, 1989",
        "(6.8/10, IMDb), (79%, Rotten Tomatoes), (4.7/5, Facebook)",
        """
        [(Homer Simpson 666 Episodes),(Dan Castellaneta, @drawable/dan_castellaneta)],
        [Marge Simpson 666 Episodes, (Julie Kavner, @drawable/julie_kavner)],
        [Bart Simpson 666 Episodes, (Nancy Cartwright, @drawable/nancy_cartwright)],
        [Lisa Simpson 666 Episodes, (Yeardley Smith, @drawable/yeardley_smith)],
        [Apu 666 Episodes, (Hank Azaria, @drawable/hank_azaria)],
        """
        )
    }
}
