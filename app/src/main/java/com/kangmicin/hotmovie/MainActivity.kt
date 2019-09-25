package com.kangmicin.hotmovie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.kangmicin.hotmovie.model.Movie

class MainActivity : AppCompatActivity(), MovieContract.View, MovieListFragment.OnListFragmentInteractionListener {

    lateinit var presenter: MovieContract.Presenter
    lateinit var movieListFragment: MovieListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = resources.obtainTypedArray(R.array.movies)
        val snapshot = Array<Array<String>>(data.length()) { i: Int ->
            val resourceId = data.getResourceId(i, 0)
            resources.getStringArray(resourceId)
        }

        presenter = MoviePresenter(this, {i -> snapshot[i]}, snapshot.size)

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
    }

    override fun displayMovies(movies: List<Movie>) {
        val bundle = Bundle()
        bundle.putParcelableArray("MOVIES", movies.toTypedArray())
        movieListFragment.arguments = bundle
    }

    override fun onListFragmentInteraction(item: Movie?) {
        val openIntent = Intent(this, MovieActivity::class.java)

        openIntent.putExtra("movie", item)
        startActivity(openIntent)
    }
}
