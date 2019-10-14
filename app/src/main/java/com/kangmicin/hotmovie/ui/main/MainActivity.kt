package com.kangmicin.hotmovie.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.IdRes
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

class MainActivity : AppActivity(),
    ListItemFragment.OnListFragmentInteractionListener,
    InfoErrorFragment.OnFragmentInteractionListener
{

    private lateinit var movieFactory: MoviesViewModelFactory
    private lateinit var  tvFactory: TvsViewModelFactory
    private lateinit var  movieModel: MoviesViewModel
    private lateinit var  tvModel: TvsViewModel

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

        movieFactory = InjectorUtils.provideMoviesViewModelFactory()
        tvFactory = InjectorUtils.provideTvViewModelFactory()
        movieModel = ViewModelProviders.of(this, movieFactory).get(MoviesViewModel::class.java)
        tvModel = ViewModelProviders.of(this, tvFactory).get(TvsViewModel::class.java)

        bottom_navigation?.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener handleMenuItemClick(it.itemId)
        }

        movieModel.getMovies().observe(this, Observer<List<Movie>> {initUi(it)})
        tvModel.getTvs().observe(this, Observer<List<Tv>> {initUi(it)})

        movieModel.hasError().observe(this, Observer<Boolean> {
            if (it == true) {
                displayError()
            }
        })

        tvModel.hasError().observe(this, Observer<Boolean> {
            if (it == true) {
                displayError()
            }
        })

        bottom_navigation?.selectedItemId = R.id.show_movie_menu
    }

    override fun onLanguageChange() {
        super.onLanguageChange()
        // reset on language changed
        handleMenuItemClick(bottom_navigation.selectedItemId)
    }

    private fun handleMenuItemClick(@IdRes it: Int): Boolean {
        return when (it) {
            R.id.show_movie_menu -> {
                movieModel.loadMovies()
                true
            }
            R.id.show_tvshow_menu -> {
                tvModel.loadTvShows()
                true
            }
            else -> false
        }
    }

    override fun onRetryInteraction() {
        handleMenuItemClick(bottom_navigation.selectedItemId)
    }

    private fun displayError() {
        val message = getString(R.string.error_subtitle)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_fragment_container,
                InfoErrorFragment.newInstance(message)
            )
            .commit()

    }

    private fun initUi(data: List<Parcelable>) {
        when {
            data.isEmpty() -> initLoading()
            else -> initView(data)
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
