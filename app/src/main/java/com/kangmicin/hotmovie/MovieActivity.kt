package com.kangmicin.hotmovie

import android.graphics.*
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.kangmicin.hotmovie.model.Movie
import kotlinx.android.synthetic.main.activity_movie.toolbar

class MovieActivity : AppCompatActivity() {

    lateinit var plotView: TextView
    lateinit var titleView: TextView
    lateinit var plotHeroView: ImageView
    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        plotHeroView = findViewById(R.id.movie_poster_hero)
        plotView = findViewById(R.id.movie_plot)
        titleView = findViewById(R.id.movie_title)

        movie = intent.getParcelableExtra("movie")

        val identity = Utils.getIdentity(this, movie.poster, Utils.ResType.DRAWABLE)
//        val bitmap = BitmapFactory.decodeResource(resources, identity)
//        val rImage = RoundedBitmapDrawableFactory.create(resources, bitmap)
//        val canvas = Canvas(bitmap)
//        canvas.drawRect(0f, 0f, bitmap.width / 2f, bitmap.height / 2f, rImage.paint)
//        canvas.drawRect(bitmap.width / 2f, 0f, bitmap.width + 0f, bitmap.height / 2f, rImage.paint)
//        rImage.draw(canvas)
////        rImage.paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
//        rImage.cornerRadius = resources.getDimension(R.dimen.corner)

        plotHeroView.clipToOutline = true
        plotHeroView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, -20, view!!.width, view.height, 50f)
            }

        }
        plotHeroView.setImageResource(identity)

        plotView.text = movie.plot
        titleView.text = movie.title

        plotHeroView.setImageResource(identity)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
