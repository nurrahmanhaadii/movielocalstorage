package id.haadii.moviecataloguelocalstorage.repository.local.asyncProcess

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.haadii.moviecataloguelocalstorage.repository.local.LocalStorage
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity
import java.lang.ref.WeakReference

class GetTvShowData (context: Context, private val listener: (LiveData<List<TvShowEntity>>) -> Unit) : AsyncTask<Void, Void, LiveData<List<TvShowEntity>>?>() {
    private val activityReference : WeakReference<Context> = WeakReference(context)
    override fun doInBackground(vararg p0: Void?): LiveData<List<TvShowEntity>>? {
        return LocalStorage(activityReference.get()!!).FavoriteTvShowDao().getAll()
    }

    override fun onPostExecute(result: LiveData<List<TvShowEntity>>?) {
        if (result != null) {
            listener(result)
        }
    }
}