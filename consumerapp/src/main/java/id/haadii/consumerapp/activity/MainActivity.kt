package id.haadii.consumerapp.activity

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.haadii.consumerapp.R
import id.haadii.consumerapp.adapter.MovieAdapter
import id.haadii.consumerapp.db.DatabaseContract
import id.haadii.consumerapp.detail.DetailActivity
import id.haadii.consumerapp.helper.MappingHelper
import id.haadii.consumerapp.model.DataItemMovie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("inmain", "true")

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                Log.e("onChange", "true")
                loadMoviesAsync()
            }
        }

        contentResolver.registerContentObserver(DatabaseContract.MovieColumns.CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadMoviesAsync()
        }
    }

    private fun loadMoviesAsync() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null) as Cursor
        Binder.restoreCallingIdentity(identityToken)
val ll = ArrayList<DataItemMovie>()
        Log.e("cursor", "${MappingHelper.mapCursorToArrayList(cursor)}")
//        Log.e("cursor", "${MappingHelper.mapCursorToObject(cursor)}")

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
//            ll.add(MappingHelper.mapCursorToObject(cursor))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
                Log.e("llll", "$title")

            }
        }



        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null) as Cursor
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val movies = deferredMovies.await()
            Log.e("movies", "$movies: ${movies.size}")
            if (movies.size > 0) {
                rv_movie.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = MovieAdapter(context, movies) { movie ->
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("movie", movie)
                        startActivity(intent)
                    }
                    pb_movie.visibility = View.GONE
                }
            } else {
                tv_movie_empty.visibility = View.VISIBLE
            }
        }
    }
}
