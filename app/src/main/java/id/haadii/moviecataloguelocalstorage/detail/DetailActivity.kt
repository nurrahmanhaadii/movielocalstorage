package id.haadii.moviecataloguelocalstorage.detail

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.BACKDROP
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.POPULARITY
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.POSTER
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.RELEASE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.TITLE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.VOTE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion._ID
import id.haadii.moviecataloguelocalstorage.movie.DataItemMovie
import id.haadii.moviecataloguelocalstorage.repository.Constants.Companion.IMG_URL
import id.haadii.moviecataloguelocalstorage.tvShow.DataItemTv
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var movie : DataItemMovie
    private lateinit var tv : DataItemTv

    private lateinit var uriWithId: Uri
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.checkMovieOrTv(intent.getParcelableExtra("movie") as DataItemMovie?)
        if (viewModel.isMovie) {
            movie = intent.getParcelableExtra("movie") as DataItemMovie
            setViewMovie(movie)
            viewModel.setMovieEntity(movie)
            viewModel.allMovies.observe(this, Observer {
                viewModel.checkFavorite(movie, it)
            })
        } else {
            tv = intent.getParcelableExtra("tv_show") as DataItemTv
            setViewTv(tv)
            viewModel.setTvShowEntity(tv)
            viewModel.allTvShows.observe(this, Observer {
                viewModel.checkFavoriteTv(tv, it)
            })
        }

    }

    private fun setViewMovie(movie: DataItemMovie) {

        title = movie.title

        Glide.with(this)
            .load(IMG_URL + movie.backdrop_path)
            .into(img_background)

        Glide.with(this)
            .load(IMG_URL + movie.poster_path)
            .into(img_photo_detail)

        tv_tittle_detail.text = movie.title
        tv_date_detail.text = movie.release_date
        tv_runtime.text = getString(R.string.popularity, movie.popularity)
        tv_overview_content.text = movie.overview
        tv_genre_content.text = movie.vote_count
    }

    private fun setViewTv(tv: DataItemTv) {
        title = tv.name

        Glide.with(this)
            .load(IMG_URL + tv.backdrop_path)
            .into(img_background)

        Glide.with(this)
            .load(IMG_URL + tv.poster_path)
            .into(img_photo_detail)

        tv_tittle_detail.text = tv.name
        tv_date_detail.text = tv.first_air_date
        tv_runtime.text = getString(R.string.popularity, tv.popularity)
        tv_overview_content.text = tv.overview
        tv_genre_content.text = tv.vote_count
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        if (viewModel.isFavorite) {
            menu?.findItem(R.id.menu_favorite)?.icon = resources.getDrawable(R.drawable.ic_favorite)
        } else {
            menu?.findItem(R.id.menu_favorite)?.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.menu_favorite -> {
                viewModel.checkMovieOrTv(intent.getParcelableExtra("movie") as DataItemMovie?)
                if (viewModel.isMovie) {
                    val values = ContentValues()
                    values.put(_ID, movie.id)
                    values.put(TITLE, movie.title)
                    values.put(RELEASE, movie.release_date)
                    values.put(OVERVIEW, movie.overview)
                    values.put(VOTE, movie.vote_count)
                    values.put(POPULARITY, movie.popularity)
                    values.put(POSTER, movie.poster_path)
                    values.put(BACKDROP, movie.backdrop_path)
                    if (viewModel.isFavorite) {
                        viewModel.deleteMovie(viewModel.getMovieEntity())
                        viewModel.isFavorite = false
                        item.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)

                        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)

                        contentResolver.delete(uriWithId, null, null)

                    } else {
                        viewModel.insertMovie(viewModel.getMovieEntity())
                        viewModel.isFavorite = true
                        item.icon = resources.getDrawable(R.drawable.ic_favorite)

                        contentResolver.insert(CONTENT_URI, values)
                        Toast.makeText(this, "Satu item berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (viewModel.isFavorite) {
                        viewModel.deleteTvShow(viewModel.getTvShowEntity())
                        viewModel.isFavorite = false
                        item.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
                    } else {
                        viewModel.insertTvShow(viewModel.getTvShowEntity())
                        viewModel.isFavorite = true
                        item.icon = resources.getDrawable(R.drawable.ic_favorite)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
