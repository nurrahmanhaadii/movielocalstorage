package id.haadii.moviecataloguelocalstorage.repository.local

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.haadii.moviecataloguelocalstorage.repository.local.dao.FavoriteTvShowDao
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity

class TvShowRepository (application: Application) {

    private var tvDao : FavoriteTvShowDao
    private var allTvShow : LiveData<List<TvShowEntity>>

    init {
        val db = LocalStorage(application)
        tvDao = db.FavoriteTvShowDao()
        allTvShow = tvDao.getAll()
    }

    fun getAll(): LiveData<List<TvShowEntity>> {
        return allTvShow
    }

    fun insert(tvShowEntity: TvShowEntity) {
        InsertAsyncTask(tvDao).execute(tvShowEntity)
    }

    fun delete(tvShowEntity: TvShowEntity) {
        DeleteAsyncTask(tvDao).execute(tvShowEntity)
    }

    inner class InsertAsyncTask internal constructor(dao: FavoriteTvShowDao): AsyncTask<TvShowEntity, Void, Void>() {
        private val mAsyncTask: FavoriteTvShowDao = dao

        override fun doInBackground(vararg p0: TvShowEntity): Void? {
            mAsyncTask.insert(p0[0])
            return null
        }
    }

    inner class DeleteAsyncTask internal constructor(dao: FavoriteTvShowDao): AsyncTask<TvShowEntity, Void, Void>() {
        private val mAsyncTask: FavoriteTvShowDao = dao

        override fun doInBackground(vararg p0: TvShowEntity): Void? {
            mAsyncTask.delete(p0[0])
            return null
        }
    }
}