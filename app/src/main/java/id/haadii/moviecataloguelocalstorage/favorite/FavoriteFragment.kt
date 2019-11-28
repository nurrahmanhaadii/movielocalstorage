package id.haadii.moviecataloguelocalstorage.favorite


import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import id.haadii.moviecataloguelocalstorage.helper.MappingHelper
import id.haadii.moviecataloguelocalstorage.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)
        viewModel.getFavoriteMovie().observe(this, androidx.lifecycle.Observer {
            viewModel.setFavoriteMovie(it)
        })
        viewModel.getFavoriteTvShow().observe(this, androidx.lifecycle.Observer {
            viewModel.setFavoriteTvShow(it)
        })

        view_pager.adapter = MyPagerAdapter(childFragmentManager, activity!!)
        tab.setupWithViewPager(view_pager)
    }

    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = activity!!.contentResolver.query(CONTENT_URI, null, null, null, null) as Cursor
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val movies = deferredMovies.await()
            Log.e("moviessss", "$movies")
            if (movies.size > 0) {

            }
        }
    }

}
