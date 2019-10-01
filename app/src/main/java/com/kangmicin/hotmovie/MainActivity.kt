package com.kangmicin.hotmovie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.TvShow

class MainActivity : AppCompatActivity(), MovieContract.View, MovieListFragment.OnListFragmentInteractionListener {
    override fun displayTvShows(shows: List<TvShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var presenter: MovieContract.Presenter
    lateinit var movieListFragment: MovieListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = resources.obtainTypedArray(R.array.movies)
        val tvShowData = resources.obtainTypedArray(R.array.shows)
        val snapshot = Array<Array<String>>(data.length()) { i: Int ->
            val resourceId = data.getResourceId(i, 0)
            resources.getStringArray(resourceId)
        }

        val tvShowSnapshot =  Array<Array<String>>(data.length()) { i: Int ->
            val resourceId = tvShowData.getResourceId(i, 0)
            resources.getStringArray(resourceId)
        }

        presenter = MoviePresenter(this, {i, type ->
            when(type) {
                MoviePresenter.ModelType.MOVIE -> snapshot[i]
                MoviePresenter.ModelType.TV_SHOW -> tvShowSnapshot[i]
            }
        }, 10)

        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                if (f is MovieListFragment) {
                    movieListFragment = f
                    presenter.loadMovies()
                }
                super.onFragmentPreAttached(fm, f, context)
            }
        }, false)

        setContentView(R.layout.activity_main)

        presenter.loadMovies()
        data.recycle()
        tvShowData.recycle()
    }

    override fun displayMovies(movies: List<Movie>) {
        val bundle = Bundle()
        bundle.putParcelableArray(MovieListFragment.MOVIES_KEY, movies.toTypedArray())
        movieListFragment.arguments = bundle
    }

    override fun onListFragmentInteraction(item: Movie?) {
        val openIntent = Intent(this, MovieActivity::class.java)

        openIntent.putExtra(MovieActivity.MOVIE_KEY, item)
        startActivity(openIntent)
    }
}
