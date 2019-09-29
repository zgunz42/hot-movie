package com.kangmicin.hotmovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kangmicin.hotmovie.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieAdapter(
    var movies: List<Movie>,
    var context: Context,
    var listener: MovieListFragment.OnListFragmentInteractionListener?
) : BaseAdapter() {
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = if (convertView === null) {
            inflater.inflate(R.layout.fragment_movie, parent, false)
        } else {
            convertView
        }

        with(movies[position], {
            val posterImage = Utils.roundedImage(context, this.poster, R.dimen.corner)

            view.movie_poster.setImageDrawable(posterImage)
            view.movie_title.text = this.title
            view.movie_plot.text = this.plot
            view.movie_item.tag = this
        })

        listener?.run {
            view.movie_item.setOnClickListener { v -> onListFragmentInteraction(v.tag as Movie) }
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movies.size
    }
}