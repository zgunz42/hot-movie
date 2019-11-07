package com.kangmicin.hotmovie.ui.main.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.data.entity.Tv
import com.kangmicin.hotmovie.ui.main.tvs.TvsFragment

class TvFavoriteFragment: TvsFragment() {
    override fun initData(): LiveData<List<DisplayData>> {
        return Transformations.map(super.initData()){
            it.filter {w ->
                if (w.getTag() is Tv) {
                    (w.getTag() as Tv).isFavorite
                }else {
                    false
                }
            }
        }
    }
}