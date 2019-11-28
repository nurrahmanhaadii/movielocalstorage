package id.haadii.moviecataloguelocalstorage.favorite.favoriteMovie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.main.MainViewModel
import java.util.*

import id.haadii.moviecataloguelocalstorage.detail.DetailActivity
import id.haadii.moviecataloguelocalstorage.movie.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.pb_movie

class FavoriteMovieFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)

        tv_movie_empty.visibility = View.VISIBLE

        initObserver()
    }

    private fun initObserver() {
        viewModel.favoriteMovieObserver.observe(this, androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                tv_movie_empty.visibility = View.GONE
                rv_movie.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity)
                    adapter = MovieAdapter(
                        activity!!,
                        it
                    ) { movie ->
                        val intent = Intent(
                            activity,
                            DetailActivity::class.java
                        )
                        intent.putExtra("movie", movie)
                        startActivity(intent)
                    }
                    pb_movie.visibility = View.GONE
                }
            } else {
                pb_movie.visibility = View.GONE
                tv_movie_empty.visibility = View.VISIBLE
            }
        })
    }

}
