package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import java.lang.ref.WeakReference

class GetMovieData(context: Context, private val listener: (List<MovieEntity>) -> Unit) {
//    AsyncTask<Void, Void, List<MovieEntity>>() {
//    private val activityReference : WeakReference<Context> = WeakReference(context)
//    override fun doInBackground(vararg p0: Void?): List<MovieEntity> {
//        return LocalStorage(activityReference.get()!!).FavoriteMovieDao().getAll()
//    }
//
//    override fun onPostExecute(result: List<MovieEntity>?) {
//        if (result != null) {
//            listener(result)
//
//        }
//    }
}