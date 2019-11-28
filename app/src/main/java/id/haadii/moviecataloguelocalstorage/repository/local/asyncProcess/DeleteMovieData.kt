package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import java.lang.ref.WeakReference

class DeleteMovieData(context: Context, var movieEntity: MovieEntity) : AsyncTask<Void, Void, Boolean>() {
    private val activityReference : WeakReference<Context> = WeakReference(context)

    override fun doInBackground(vararg p0: Void?): Boolean {
        LocalStorage(activityReference.get()!!).FavoriteMovieDao()?.delete(movieEntity)
        return true
    }

    override fun onPostExecute(result: Boolean?) {
        if (result!!) {
            Log.e("deletedatabase","success")
            Toast.makeText(activityReference.get(), "Unfavorite ${movieEntity.title}", Toast.LENGTH_LONG).show()
        }
    }
}