package com.kangmicin.hotmovie.data.core

import androidx.room.*
import com.kangmicin.hotmovie.data.entity.Tv

@Dao
interface TvDAO {
    @Query("SELECT * FROM tv")
    fun getAll(): List<Tv>

    @Query("SELECT * FROM tv WHERE id IN (:tvIds)")
    fun loadAllByIds(tvIds: IntArray): List<Tv>

    @Query("SELECT * FROM tv WHERE isFavorite = 1")
    fun loadAllFavorite(): List<Tv>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvs(vararg tvs: Tv)

    @Insert
    fun insertTv(tv: Tv)

    @Update
    fun updateTvs(vararg tvs: Tv)

    @Delete
    fun deleteTvs(vararg tvs: Tv)
}