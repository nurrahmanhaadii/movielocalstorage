package id.haadii.moviecataloguelocalstorage.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity

@Dao
interface FavoriteTvShowDao {
    @Query("SELECT * FROM tv_items")
    fun getAll() : LiveData<List<TvShowEntity>>

    @Insert
    fun insert(vararg movieEntity: TvShowEntity)

    @Delete
    fun delete(movieEntity: TvShowEntity)

    @Update
    fun update(vararg movieEntity: TvShowEntity)
}