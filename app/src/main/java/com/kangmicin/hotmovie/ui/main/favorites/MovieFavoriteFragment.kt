package com.kangmicin.hotmovie.ui.main.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.data.entity.Movie
import com.kangmicin.hotmovie.ui.main.movies.MoviesFragment

class MovieFavoriteFragment : MoviesFragment() {
    override fun initData(): LiveData<List<DisplayData>> {
        return Transformations.map(super.initData()){
            val result = it.filter {w ->
                if (w.getTag() is Movie) {
                    (w.getTag() as Movie).isFavorite
                }else {
                    false
                }
            }
            Log.i("favorite", result.size.toString())
            result
        }
    }
}