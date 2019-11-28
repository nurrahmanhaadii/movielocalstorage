package id.haadii.moviecataloguelocalstorage.repository.network

import id.haadii.moviecataloguelocalstorage.movie.Movie
import id.haadii.moviecataloguelocalstorage.tvShow.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun movie(@Query("api_key") value: String,
              @Query("language") lang: String) : Call<Movie>

    @GET("tv")
    fun tv(@Query("api_key") value: String,
           @Query("language") lang: String) : Call<TvShow>

    @GET("movie")
    fun searchMovie(@Query("api_key") value: String,
                    @Query("language") lang: String,
                    @Query("query") query: String) : Call<Movie>

    @GET("tv")
    fun searchTv(@Query("api_key") value: String,
                 @Query("language") lang: String,
                 @Query("query") query: String) : Call<TvShow>

    @GET("movie")
    fun releaseMovie(@Query("api_key") value: String,
                     @Query("primary_release_date.gte") gte : String,
                     @Query("primary_release_date.lte") lte : String) : Call<Movie>

}