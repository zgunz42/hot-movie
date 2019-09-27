package com.kangmicin.hotmovie

import android.graphics.*
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.Person
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.actor_card.view.*
import kotlinx.android.synthetic.main.content_movie.*
import java.text.SimpleDateFormat
import java.util.*

class MovieActivity : AppCompatActivity() {
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        movie = intent.getParcelableExtra("movie")

        val identity = Utils.getIdentity(this, movie.poster, Utils.ResType.DRAWABLE)

        movie_poster_hero.clipToOutline = true
        movie_poster_hero.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, -20, view!!.width, view.height, 50f)
            }

        }

        movie.rating.forEach {
            if (it.source === "IMDb") {
                imdb_rating.text = it.amount
            }

            if (it.source === "Rotten Tomatoes") {
                rotten_tomatoes_rating.text = it.amount
            }

            if (it.source === "Metacritic") {
                metacritic_rating.text = it.amount
            }
        }

        movie_poster_hero.setImageResource(identity)

        movie_plot.text = movie.plot
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

        movie_title.text = spannable
        movie_poster.setImageDrawable(rImage)
        movie_release.text = createDetailText("Release Date",releaseFormat.format(movie.release))
        movie_director.text = createDetailText("Director", movie.director.name)

        movie_length.text = Utils.getTimeFormat(movie.length)

        val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.START
        param.marginEnd = resources.getDimensionPixelSize(R.dimen.content_spacing)
        movie.genre.forEach {
            movie_genres.addView(makeGenreView(it), param)
        }

        val actorParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.START
        param.marginEnd = resources.getDimensionPixelSize(R.dimen.content_spacing)

        movie.actors.forEach {
            movie_actors.addView(makeActorView(it.value, it.key), actorParam)
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

    private fun makeActorView(role: String, actor: Person): View {
        val view = layoutInflater.inflate(R.layout.actor_card, movie_actors, false)

        actor.profileUrl?.let {

            val drawable = Utils.roundedImage(this, it, R.dimen.corner)
            view.movie_actor_picture?.setImageDrawable(drawable)
        }
        view.movie_actor_name?.text = actor.name
        view.movie_actor_role?.text = role

        return view
    }
}
