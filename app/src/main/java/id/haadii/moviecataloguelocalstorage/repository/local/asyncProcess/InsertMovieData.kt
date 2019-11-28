package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import java.lang.ref.WeakReference

class InsertMovieData(context: Context, var movieEntity: MovieEntity) : AsyncTask<Void, Void, Boolean>() {
    private val activityReference : WeakReference<Context> = WeakReference(context)
    override fun doInBackground(vararg params: Void?): Boolean {
        LocalStorage(activityReference.get()!!).FavoriteMovieDao()?.insert(movieEntity)
        return true
    }
    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Log.e("adddaabase","success")
            Toast.makeText(activityReference.get(), "Favorite ${movieEntity.title}", Toast.LENGTH_LONG).show()
        }
    }
}