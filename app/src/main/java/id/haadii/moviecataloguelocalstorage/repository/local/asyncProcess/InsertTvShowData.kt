package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity
import java.lang.ref.WeakReference

class InsertTvShowData(context: Context, var tvShowEntity: TvShowEntity) : AsyncTask<Void, Void, Boolean>() {
    private val activityReference : WeakReference<Context> = WeakReference(context)
    override fun doInBackground(vararg params: Void?): Boolean {
        LocalStorage(activityReference.get()!!).FavoriteTvShowDao().insert(tvShowEntity)
        return true
    }
    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Log.e("adddaabase","success")
            Toast.makeText(activityReference.get(), "Favorite ${tvShowEntity.name}", Toast.LENGTH_LONG).show()
        }
    }
}