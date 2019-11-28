package id.haadii.moviecataloguelocalstorage.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.haadii.moviecataloguelocalstorage.BuildConfig
import id.haadii.moviecataloguelocalstorage.movie.DataItemMovie
import id.haadii.moviecataloguelocalstorage.movie.Movie
import id.haadii.moviecataloguelocalstorage.repository.local.MovieRepository
import id.haadii.moviecataloguelocalstorage.repository.local.TvShowRepository
import id.haadii.moviecataloguelocalstorage.repository.local.entity.MovieEntity
import id.haadii.moviecataloguelocalstorage.repository.local.entity.TvShowEntity
import id.haadii.moviecataloguelocalstorage.repository.network.NetworkConfig
import id.haadii.moviecataloguelocalstorage.tvShow.DataItemTv
import id.haadii.moviecataloguelocalstorage.tvShow.TvShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository = MovieRepository(application)
    private val allMovies: LiveData<List<MovieEntity>>

    private val tvShowRepository = TvShowRepository(application)
    private val allTvShows: LiveData<List<TvShowEntity>>

    private var dataMovie = MutableLiveData<Movie>()
    private var dataTvShow = MutableLiveData<TvShow>()
    private var searchMovie = MutableLiveData<Movie>()
    private var searchTvShow = MutableLiveData<TvShow>()
    var favoriteMovieObserver = MutableLiveData<ArrayList<DataItemMovie>>()
    var favoriteTvShowObserver = MutableLiveData<ArrayList<DataItemTv>>()

    private var favoriteMovieList = ArrayList<DataItemMovie>()
    private var favoriteTvShowList = ArrayList<DataItemTv>()

    init {
        allMovies = movieRepository.getAll()
        allTvShows = tvShowRepository.getAll()
        getMovie()
        getTvShow()
    }

    fun getFavoriteMovie() : LiveData<List<MovieEntity>> {
        return allMovies
    }

    fun getMovie() {
        NetworkConfig().api().movie(BuildConfig.API_KEY, "en-US").enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    dataMovie.value = response.body()
                }
            }
        })
    }

    fun getFavoriteTvShow() : LiveData<List<TvShowEntity>> {
        return allTvShows
    }

    fun getTvShow() {
        NetworkConfig().api().tv(BuildConfig.API_KEY, "en-US").enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                dataTvShow.value = response.body()
            }
        })
    }

    fun getSearchMovie(query: String) {
        NetworkConfig().apiSearch().searchMovie(BuildConfig.API_KEY, "en-US", query).enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                searchMovie.value = response.body()
            }
        })
    }

    fun getSearchTv(query: String) {
        NetworkConfig().apiSearch().searchTv(BuildConfig.API_KEY, "en-US", query).enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                searchTvShow.value = response.body()
            }
        })
    }

    fun setMovie() : MutableLiveData<Movie> {
        return dataMovie
    }

    fun setTvShow() : MutableLiveData<TvShow> {
        return dataTvShow
    }

    fun setSearchMovie() : MutableLiveData<Movie> {
        return searchMovie
    }

    fun setSearchTvShow() : MutableLiveData<TvShow> {
        return searchTvShow
    }

    fun setFavoriteMovie(movieEntity: List<MovieEntity>) {
        favoriteMovieList.clear()
        for (item in movieEntity) {
            val favoriteMovie = DataItemMovie(
                id = item.id,
                title = item.title,
                release_date = item.release_date,
                overview = item.overview,
                popularity = item.popularity,
                vote_count = item.vote_count,
                poster_path = item.poster_path,
                backdrop_path = item.backdrop_path)

            favoriteMovieList.add(favoriteMovie)
        }
        favoriteMovieObserver.postValue(favoriteMovieList)
    }

    fun setFavoriteTvShow(tvShowEntity: List<TvShowEntity>) {
        favoriteTvShowList.clear()
        for (tv in tvShowEntity) {
            val favoriteTv = DataItemTv(
                id = tv.id,
                name = tv.name,
                first_air_date = tv.first_air_date,
                popularity = tv.popularity,
                overview = tv.overview,
                vote_count = tv.vote_count,
                poster_path = tv.poster_path,
                backdrop_path = tv.backdrop_path
            )
            favoriteTvShowList.add(favoriteTv)
        }
        favoriteTvShowObserver.postValue(favoriteTvShowList)
    }
}