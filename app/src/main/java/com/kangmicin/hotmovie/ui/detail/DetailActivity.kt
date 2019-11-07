package com.kangmicin.hotmovie.ui.detail

import android.graphics.Color
import android.graphics.Outline
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.Person
import com.kangmicin.hotmovie.data.entity.Rating
import com.kangmicin.hotmovie.databinding.ActorCardBinding
import com.kangmicin.hotmovie.ui.AppActivity
import com.kangmicin.hotmovie.utilities.Helper
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.review_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

abstract class DetailActivity: AppActivity() {
    private lateinit var collapseListener: AppBarLayout.OnOffsetChangedListener
    private var favoriteMenuItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menu?.findItem(R.id.action_favorite)?.run {
            favoriteMenuItem = this
        }
        return true
    }

    fun setFavorite(status: Boolean) {
        when(status) {
            true -> {
                favoriteMenuItem?.icon = getDrawable(R.drawable.ic_favorite_black_24dp)
            }
            false -> {
                favoriteMenuItem?.icon = getDrawable(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        collapseListener = object:AppBarLayout.OnOffsetChangedListener {
            var preview: Int? = null
            var isHide: Boolean = true
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {

                // escape from idle state
                if (preview != p1) {
                    //collapsed
                    if (p1.absoluteValue == p0!!.totalScrollRange) {
                        supportActionBar?.title = detail_title?.text
                        isHide = false
                    }else {
                        if (!isHide) {
                            supportActionBar?.title = null
                            toolbar?.title = null
                            isHide = true
                        }
                    }

                    preview = p1
                }
            }
        }

        app_bar?.addOnOffsetChangedListener(collapseListener)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    protected fun displayMoviePoster(poster: String) {
        Glide
            .with(this)
            .load(poster) // valid url
            .placeholder(ColorDrawable(Color.GRAY))
            .apply(
                RequestOptions
                    .centerCropTransform()
                    .transform(RoundedCorners(10))
            )
            .into(detail_poster)
    }

    protected fun displayPlot(plot: String) {
        detail_plot.text = plot
    }

    protected fun displayInfoDirector(@StringRes format: Int, directors: List<String>) {
        detail_author.text = HtmlCompat.fromHtml(getString(format, directors.joinToString()), HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    protected fun displayInfoLength(duration: Long) {
        val (hours, minutes) = Helper.getTimeFormat(duration)
        detail_length.text = getString(R.string.date_format, hours, minutes)
    }

    protected fun displayTitle(title: String, release: Date) {
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val year = "( ${yearFormat.format(release)} )"
        val spannable = SpannableStringBuilder("$title $year")

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#CBFFFFFF")),
            title.length,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        detail_title?.text = spannable
    }

    protected fun displayGenres(genres: List<String>) {
        val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.START
        param.marginEnd = resources.getDimensionPixelSize(R.dimen.content_spacing)
        setOf(*genres.toTypedArray()).filter { it.isNotEmpty() }.forEach {
            detail_genres.addView(makeGenreView(it), param)
        }
    }

    protected fun displayTopActor(actors: Map<String, Person>) {
        val topActors = actors.toSortedMap(compareBy { actors[it]?.order })
        val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.START
        param.marginEnd = resources.getDimensionPixelSize(R.dimen.content_spacing)

        detail_actors.removeAllViews() // clear child
        topActors.forEach {
            Log.i("actor", "actor: $it")
            detail_actors.addView(makeActorView(it.key, it.value), param)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        app_bar?.removeOnOffsetChangedListener(collapseListener)
    }

    private fun makeActorView(role: String, actor: Person): View {
        val view = layoutInflater.inflate(R.layout.actor_card, detail_actors, false)
        val binding = DataBindingUtil.bind<ActorCardBinding>(view)

        binding?.let {
            it.movieActorPicture.clipToOutline = true
            it.movieActorPicture.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    if (view != null && outline != null) {
                        outline.setRoundRect(0, 0, view.width, view.height, 10f)
                    }
                }

            }
            it.role = role
            it.person = actor
        }

        return view
    }


    protected fun displayInfoReleaseDate(release: Date) {
        val releaseFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val content = releaseFormat.format(release)

        detail_release.text = HtmlCompat.fromHtml(getString(R.string.release_format, content),
            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS or HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    protected fun displayRatings(ratings: List<Rating>) {
        val  params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        params.weight = 1F
        ratings.forEach {
            val view = layoutInflater.inflate(R.layout.review_item, detail_reviews_container, false)
            view.rating_tag?.text = it.source
            view.rating_amount?.text = it.amount
            detail_reviews_container.addView(view, params)
        }
    }

    protected fun displayHeroPoster(poster: String) {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        detail_poster_hero.clipToOutline = true
        detail_poster_hero.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                if (view != null && outline != null) {
                    outline.setRoundRect(0, -50, view.width, view.height, 50f)
                }
            }

        }

        val dimension = resources.getDimension(R.dimen.app_bar_height)

        Glide
            .with(detail_poster_hero.context)
            .asDrawable()
            .load(poster) // valid url
            .override(size.x, dimension.toInt())
            .into(detail_poster_hero)
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