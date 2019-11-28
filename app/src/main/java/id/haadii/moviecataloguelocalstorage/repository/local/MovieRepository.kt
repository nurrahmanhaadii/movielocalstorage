package id.haadii.moviecataloguelocalstorage.repository.local

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.haadii.moviecataloguelocalstorage.repository.local.dao.FavoriteMovieDao
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity

class MovieRepository(application: Application) {

    private var movieDao : FavoriteMovieDao
    private var allMovie : LiveData<List<MovieEntity>>

    init {
        val db = LocalStorage(application)
        movieDao = db.FavoriteMovieDao()
        allMovie = movieDao.getAll()
    }

    fun getAll(): LiveData<List<MovieEntity>> {
        return allMovie
    }


    fun insert(movieEntity: MovieEntity) {
        InsertAsyncTask(movieDao).execute(movieEntity)
    }

    fun delete(movieEntity: MovieEntity) {
        DeleteAsyncTask(movieDao).execute(movieEntity)
    }

    inner class InsertAsyncTask internal constructor(dao: FavoriteMovieDao): AsyncTask<MovieEntity, Void, Void>() {
        private val mAsyncTaskDao: FavoriteMovieDao = dao

        override fun doInBackground(vararg params: MovieEntity): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    inner class DeleteAsyncTask internal constructor(dao: FavoriteMovieDao): AsyncTask<MovieEntity, Void, Void>() {
        private val mAsyncTask: FavoriteMovieDao = dao

        override fun doInBackground(vararg p0: MovieEntity): Void? {
            mAsyncTask.delete(p0[0])
            return null
        }
    }
}