package com.kangmicin.hotmovie

import android.content.res.ColorStateList
import android.graphics.*
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginEnd
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import com.kangmicin.hotmovie.model.Movie
import kotlinx.android.synthetic.main.activity_movie.*
import java.text.SimpleDateFormat
import java.util.*

class MovieActivity : AppCompatActivity() {

    lateinit var plotView: TextView
    lateinit var titleView: TextView
    lateinit var plotHeroView: ImageView
    lateinit var imdbRatingView: TextView
    lateinit var rotttenTommatoes: TextView
    lateinit var metacriticView: TextView
    lateinit var movieLengthView: TextView
    lateinit var moviePosterView: ImageView
    lateinit var movieDirectorView: TextView
    lateinit var movieReleaseView: TextView
    lateinit var movieGenresLayout: LinearLayout
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        plotHeroView = findViewById(R.id.movie_poster_hero)
        plotView = findViewById(R.id.movie_plot)
        moviePosterView = findViewById(R.id.movie_poster)
        movieDirectorView = findViewById(R.id.movie_director)
        movieReleaseView = findViewById(R.id.movie_release)
        titleView = findViewById(R.id.movie_title)
        imdbRatingView = findViewById(R.id.imdb_rating)
        movieLengthView = findViewById(R.id.movie_length)
        rotttenTommatoes = findViewById(R.id.rotten_tomatoes_rating)
        metacriticView = findViewById(R.id.metacritic_rating)
        movieGenresLayout = findViewById(R.id.movie_genres)

        movie = intent.getParcelableExtra("movie")

        val identity = Utils.getIdentity(this, movie.poster, Utils.ResType.DRAWABLE)

        plotHeroView.clipToOutline = true
        plotHeroView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, -20, view!!.width, view.height, 50f)
            }

        }

        movie.rating.forEach {
            if (it.source === "IMDb") {
                imdbRatingView.text = it.amount
            }

            if (it.source === "Rotten Tomatoes") {
                rotttenTommatoes.text = it.amount
            }

            if (it.source === "Metacritic") {
                metacriticView.text = it.amount
            }
        }

        plotHeroView.setImageResource(identity)

        plotView.text = movie.plot
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val releaseFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)
        val year = "( ${yearFormat.format(movie.release)} )"

        val spannable = SpannableStringBuilder("${movie.title} $year")
        val rImage = Utils.roundedImage(this, movie.poster, R.dimen.corner)

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#CBFFFFFF")),
            movie.title.length,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        titleView.text = spannable
        moviePosterView.setImageDrawable(rImage)
        movieReleaseView.text = createDetailText("Release Date",releaseFormat.format(movie.release))
        movieDirectorView.text = createDetailText("Director", movie.director.name)
        plotHeroView.setImageResource(identity)

        movieLengthView.text = Utils.getTimeFormat(movie.length)

        val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.START
        param.marginEnd = resources.getDimensionPixelSize(R.dimen.content_spacing)
        movie.genre.forEach {
            movie_genres.addView(makeGenreView(it), param)
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createDetailText(tag: String, content: String): SpannableStringBuilder {
        val description = "$tag: "
        val spannable = SpannableStringBuilder("$description$content")

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            description.length,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return spannable
    }

    private fun makeGenreView(genre: String): TextView {

        val textView = TextView(this)
        textView.text = genre
        textView.updatePadding(25, 10, 20, 15)
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.setTextColor(Color.WHITE)
        textView.background = getDrawable(R.drawable.round_outline_poster)
        return textView
    }
}
