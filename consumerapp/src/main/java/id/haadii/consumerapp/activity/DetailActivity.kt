package id.haadii.consumerapp.detail

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.haadii.consumerapp.Constants.Companion.IMG_URL
import id.haadii.consumerapp.model.DataItemMovie
import id.haadii.consumerapp.R
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var movie : DataItemMovie

    private lateinit var uriWithId: Uri
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movie = intent.getParcelableExtra("movie") as DataItemMovie
        setViewMovie(movie)

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
            menu?.findItem(R.id.menu_favorite)?.icon = resources.getDrawable(R.drawable.ic_favorite)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.menu_favorite -> {
                item.icon = resources.getDrawable(R.drawable.ic_favorite_not_yet)
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)
                contentResolver.delete(uriWithId, null, null)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
