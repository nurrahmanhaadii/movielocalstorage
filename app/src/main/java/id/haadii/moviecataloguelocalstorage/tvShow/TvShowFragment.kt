package id.haadii.moviecataloguelocalstorage.tvShow


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
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_tv_show.*
import java.util.*

class TvShowFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)

        viewModel.setTvShow().observe(this, androidx.lifecycle.Observer {
            rv_tv_show.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = TvShowAdapter(context, it.results) { tv ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("tv_show", tv)
                    startActivity(intent)
                }
                pb_tv_show.visibility = View.GONE
            }
        })

        viewModel.setSearchTvShow().observe(this, androidx.lifecycle.Observer {
            rv_tv_show.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = TvShowAdapter(context, it.results) { tv ->
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("tv_show", tv)
                    startActivity(intent)
                }
                pb_tv_show.visibility = View.GONE
            }
        })
    }

}
