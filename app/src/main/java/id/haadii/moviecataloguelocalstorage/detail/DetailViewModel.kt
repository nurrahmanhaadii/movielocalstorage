package id.haadii.moviecataloguelocalstorage.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.haadii.moviecataloguelocalstorage.movie.DataItemMovie
import id.haadii.moviecataloguelocalstorage.repository.local.MovieRepository
import id.haadii.moviecataloguelocalstorage.repository.local.TvShowRepository
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity
import id.haadii.moviecataloguelocalstorage.tvShow.DataItemTv

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository = MovieRepository(application)
    val allMovies: LiveData<List<MovieEntity>>

    private val tvShowRepository = TvShowRepository(application)
    val allTvShows: LiveData<List<TvShowEntity>>

    var isFavorite : Boolean = false
    var isMovie : Boolean = false
    private var movieEntity : MovieEntity? = null
    private var tvEntity : TvShowEntity? = null

    init {
        allMovies = movieRepository.getAll()
        allTvShows = tvShowRepository.getAll()
    }

    fun insertMovie(movieEntity: MovieEntity) {
        movieRepository.insert(movieEntity)
    }

    fun deleteMovie(movieEntity: MovieEntity) {
        movieRepository.delete(movieEntity)
    }

    fun insertTvShow(tvShowEntity: TvShowEntity) {
        tvShowRepository.insert(tvShowEntity)
    }

    fun deleteTvShow(tvShowEntity: TvShowEntity) {
        tvShowRepository.delete(tvShowEntity)
    }

    fun checkMovieOrTv(movie: DataItemMovie?) {
        if (movie != null) {
            isMovie = true
        }
    }

    fun setMovieEntity (movie: DataItemMovie) {
        movieEntity = MovieEntity(
            id = movie.id,
            title = movie.title,
            release_date = movie.release_date,
            popularity = movie.popularity,
            backdrop_path = movie.backdrop_path,
            poster_path = movie.poster_path,
            overview = movie.overview,
            vote_count = movie.vote_count
        )
    }

    fun setTvShowEntity (tv: DataItemTv) {
        tvEntity = TvShowEntity(
            id = tv.id,
            name = tv.name,
            first_air_date = tv.first_air_date,
            popularity = tv.popularity,
            overview = tv.overview,
            vote_count = tv.vote_count,
            poster_path = tv.poster_path,
            backdrop_path = tv.backdrop_path
        )
    }

    fun getMovieEntity() : MovieEntity {
        return movieEntity!!
    }

    fun getTvShowEntity() : TvShowEntity {
        return tvEntity!!
    }

    fun checkFavorite(movie: DataItemMovie, list: List<MovieEntity>) {
        for (movieEntity in list) {
            if (movie.id == movieEntity.id) {
                isFavorite = true
                break
            }
        }
        Log.e("ifavorite", "$isFavorite")
    }

    fun checkFavoriteTv(tv: DataItemTv, list: List<TvShowEntity>) {
        for (tvEntity in list ) {
            if (tv.id == tvEntity.id) {
                isFavorite = true
                break
            }
        }
    }
}