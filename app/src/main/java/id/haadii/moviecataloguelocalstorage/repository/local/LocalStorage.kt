package id.haadii.moviecataloguelocalstorage.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.haadii.moviecataloguelocalstorage.repository.local.dao.FavoriteMovieDao
import id.haadii.moviecataloguelocalstorage.repository.local.dao.FavoriteTvShowDao
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1
)
abstract class LocalStorage : RoomDatabase() {
    abstract fun FavoriteMovieDao() : FavoriteMovieDao
    abstract fun FavoriteTvShowDao() : FavoriteTvShowDao

    companion object {
        @Volatile private var instance: LocalStorage? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also { instance = it }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(context,
            LocalStorage::class.java, "movie-catalogue.db")
            .build()
    }
}