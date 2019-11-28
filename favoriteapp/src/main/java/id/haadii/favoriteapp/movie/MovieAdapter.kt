package id.haadii.favoriteapp.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.haadii.favoriteapp.Constants.Companion.IMG_URL
import id.haadii.favoriteapp.R
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val context: Context, private val movies: ArrayList<DataItemMovie>, private val listener: (DataItemMovie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.binMovie(context, movies[position], listener)
    }

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = view.tv_title
        private val tvDate = view.tv_date
        private val tvOverview = view.tv_overview
        private val imgPhoto = view.img_photo

        fun binMovie(context: Context, movie: DataItemMovie, listener: (DataItemMovie) -> Unit) {
            tvTitle.text = movie.title
            tvDate.text = movie.release_date
            tvOverview.text = movie.overview
            Glide.with(context)
                .load(IMG_URL + movie.poster_path)
                .into(imgPhoto)

            itemView.setOnClickListener {
                listener(movie)
            }
        }
    }
}