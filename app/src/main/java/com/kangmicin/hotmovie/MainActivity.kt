package com.kangmicin.hotmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.TvShow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieContract.View, MovieListFragment.OnListFragmentInteractionListener, TvShowFragment.OnListFragmentInteractionListener {

    lateinit var presenter: MovieContract.Presenter

    companion object {
        var LAST_TAB = "LAST_TAB"
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outPersistentState?.run {
            putInt(LAST_TAB, bottom_navigation.selectedItemId)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.run {
            bottom_navigation.selectedItemId = getInt(LAST_TAB, bottom_navigation.selectedItemId)
            bottom_navigation.dispatchSetSelected(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var initView = R.id.show_movie_menu
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

        savedInstanceState?.run {
            initView = getInt(LAST_TAB, initView)
        }

        presenter = MoviePresenter(this, {i, type ->
            when(type) {
                MoviePresenter.ModelType.MOVIE -> snapshot[i]
                MoviePresenter.ModelType.TV_SHOW -> tvShowSnapshot[i]
            }
        }, 10)

        setContentView(R.layout.activity_main)

        bottom_navigation?.selectedItemId = initView
        bottom_navigation?.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.show_movie_menu -> {
                    presenter.loadMovies()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.show_tvshow_menu -> {
                    presenter.loadTvShows()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        presenter.loadMovies()
        data.recycle()
        tvShowData.recycle()
    }

    override fun displayMovies(movies: List<Movie>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, MovieListFragment.newInstance(movies))
            .commit()
    }

    override fun displayTvShows(shows: List<TvShow>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, TvShowFragment.newInstance(shows))
            .commit()
    }

    override fun onListFragmentInteraction(item: Movie?) {
        val openIntent = Intent(this, MovieActivity::class.java)

        openIntent.putExtra(MovieActivity.MOVIE_KEY, item)
        startActivity(openIntent)
    }

    override fun onListFragmentInteraction(item: TvShow?) {
        val openIntent = Intent(this, TvShowActivity::class.java)

        openIntent.putExtra(TvShowActivity.TV_SHOW_KEY, item)
        startActivity(openIntent)
    }
}
