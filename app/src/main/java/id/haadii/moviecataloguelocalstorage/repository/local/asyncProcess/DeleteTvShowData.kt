package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity
import java.lang.ref.WeakReference

class DeleteTvShowData (context: Context, var tvShowEntity: TvShowEntity) : AsyncTask<Void, Void, Boolean>() {
    private val activityReference : WeakReference<Context> = WeakReference(context)

    override fun doInBackground(vararg p0: Void?): Boolean {
        LocalStorage(activityReference.get()!!).FavoriteTvShowDao().delete(tvShowEntity)
        return true
    }

    override fun onPostExecute(result: Boolean?) {
        if (result!!) {
            Log.e("deletedatabase","success")
            Toast.makeText(activityReference.get(), "Unfavorite ${tvShowEntity.name}", Toast.LENGTH_LONG).show()
        }
    }
}