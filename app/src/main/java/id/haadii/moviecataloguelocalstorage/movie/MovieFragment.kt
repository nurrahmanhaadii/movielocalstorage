package id.haadii.moviecataloguelocalstorage.movie

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
import id.haadii.moviecataloguelocalstorage.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import java.util.*

class MovieFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)

        viewModel.setMovie().observe(this, androidx.lifecycle.Observer {
            rv_movie.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = MovieAdapter(activity!!, it.results) { movie ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("movie", movie)
                    startActivity(intent)
                }
                pb_movie.visibility = View.GONE
            }
        })

        viewModel.setSearchMovie().observe(this, androidx.lifecycle.Observer {
            rv_movie.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = MovieAdapter(activity!!, it.results) { movie ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("movie", movie)
                    startActivity(intent)
                }
                pb_movie.visibility = View.GONE
            }
        })
    }
}
