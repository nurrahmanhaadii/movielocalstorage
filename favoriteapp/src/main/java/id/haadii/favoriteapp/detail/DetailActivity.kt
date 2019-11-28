package id.haadii.favoriteapp.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.haadii.favoriteapp.Constants.Companion.IMG_URL
import id.haadii.favoriteapp.R
import id.haadii.favoriteapp.movie.DataItemMovie
import id.haadii.favoriteapp.tvShow.DataItemTv
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var movie : DataItemMovie
    private lateinit var tv : DataItemTv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

//        if (viewModel.isFavorite) {
            menu?.findItem(R.id.menu_favorite)?.icon = resources.getDrawable(R.drawable.ic_favorite)
//        } else {
//            menu?.findItem(R.id.menu_favorite)?.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
//        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
//                viewModel.checkMovieOrTv(intent.getParcelableExtra("movie") as DataItemMovie?)
//                if (viewModel.isMovie) {
//                    if (viewModel.isFavorite) {
//                        viewModel.deleteMovie(viewModel.getMovieEntity())
//                        item.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
//                    } else {
//                        viewModel.insertMovie(viewModel.getMovieEntity())
//                        item.icon = resources.getDrawable(R.drawable.ic_favorite)
//                    }
//                } else {
//                    if (viewModel.isFavorite) {
//                        viewModel.deleteTvShow(viewModel.getTvShowEntity())
//                        item.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
//                    } else {
//                        viewModel.insertTvShow(viewModel.getTvShowEntity())
//                        item.icon = resources.getDrawable(R.drawable.ic_favorite)
//                    }
//                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
