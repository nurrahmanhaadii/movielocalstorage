package id.haadii.favoriteapp.main

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import id.haadii.favoriteapp.helper.MappingHelper
import id.haadii.favoriteapp.movie.DataItemMovie
import id.haadii.favoriteapp.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var dataMovie = MutableLiveData<Movie>()

    fun getMovie(cursor: Cursor) {
        var movies = ArrayList<DataItemMovie>()
        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                MappingHelper.mapCursorToArrayList(cursor)
            }
            movies = deferredMovies.await()
        }

        Log.e("movies", "$movies")
    }


    fun setMovie() : MutableLiveData<Movie> {
        return dataMovie
    }


}