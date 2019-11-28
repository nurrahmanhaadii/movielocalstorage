package id.haadii.moviecataloguelocalstorage.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM movie_items")
    fun getAll() : LiveData<List<MovieEntity>>

    @Insert
    fun insert(vararg movieEntity: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Update
    fun update(vararg movieEntity: MovieEntity)
}