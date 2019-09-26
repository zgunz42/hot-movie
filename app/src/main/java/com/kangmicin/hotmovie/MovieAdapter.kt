package com.kangmicin.hotmovie

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.kangmicin.hotmovie.model.Movie
import org.xmlpull.v1.XmlPullParser

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
        val cardView = view.findViewById<CardView>(R.id.movie_item)

        with(movies[position], {
            val imageView: ImageView = view.findViewById(R.id.movie_poster)
            val titleView: TextView = view.findViewById(R.id.movie_title)
            val plotView: TextView = view.findViewById(R.id.movie_plot)
            val rImage = Utils.roundedImage(context, this.poster, R.dimen.corner)

            imageView.setImageDrawable(rImage)
            titleView.text = this.title
            plotView.text = this.plot

            cardView.tag = this
        })

        listener?.run {
            cardView.setOnClickListener { v -> onListFragmentInteraction(v.tag as Movie) }
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