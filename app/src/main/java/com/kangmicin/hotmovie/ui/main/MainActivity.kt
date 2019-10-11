package com.kangmicin.hotmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.Movie
import com.kangmicin.hotmovie.data.Tv
import com.kangmicin.hotmovie.ui.AppActivity
import com.kangmicin.hotmovie.ui.detail.MovieActivity
import com.kangmicin.hotmovie.ui.detail.TvShowActivity
import com.kangmicin.hotmovie.ui.setting.SettingsActivity
import com.kangmicin.hotmovie.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity(), ListItemFragment.OnListFragmentInteractionListener {
    override fun appTitle(): String {
        return getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieFactory = InjectorUtils.provideMoviesViewModelFactory()
        val tvFactory = InjectorUtils.provideTvViewModelFactory()
        val movieModel = ViewModelProviders.of(this, movieFactory).get(MoviesViewModel::class.java)
        val tvModel = ViewModelProviders.of(this, tvFactory).get(TvViewModel::class.java)

        bottom_navigation?.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.show_movie_menu -> {
                    movieModel.loadMovies()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.show_tvshow_menu -> {
                    tvModel.loadTvShows()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        movieModel.getMovies().observe(this, Observer<List<Movie>> {initUi(it)})

        tvModel.getTvs().observe(this, Observer<List<Tv>> {initUi(it)})

        bottom_navigation?.selectedItemId = R.id.show_movie_menu
    }

    private fun initUi(data: List<Parcelable>) {
        if(data.isEmpty()) {
            initLoading()
        }else {
            initView(data)
        }
    }

    private fun  initLoading() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_fragment_container,
                ProgressFragment()
            )
            .commit()
    }

    private fun  initView(data: List<Parcelable>) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_fragment_container,
                ListItemFragment.newInstance(data)
            )
            .commit()
    }

    override fun onListFragmentInteraction(item: Parcelable?) {

        if (item is Movie) {
            val openIntent = Intent(this, MovieActivity::class.java)

            openIntent.putExtra(MovieActivity.MOVIE_KEY, item)
            startActivity(openIntent)
        }

        if(item is Tv) {
            val openIntent = Intent(this, TvShowActivity::class.java)

            openIntent.putExtra(TvShowActivity.TV_SHOW_KEY, item)
            startActivity(openIntent)
        }

    }
}
