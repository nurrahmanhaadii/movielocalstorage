package id.haadii.moviecataloguelocalstorage.repository.network

import id.haadii.moviecataloguelocalstorage.BuildConfig
import id.haadii.moviecataloguelocalstorage.repository.Constants.Companion.BASE_URL
import id.haadii.moviecataloguelocalstorage.repository.Constants.Companion.SEARCH_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {
    private fun getInterceptor() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun getNetwork() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getSearchNetwork() : Retrofit {
        return Retrofit.Builder().baseUrl(SEARCH_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun api() : ApiService {
        return getNetwork().create(ApiService::class.java)
    }

    fun apiSearch() : ApiService {
        return getSearchNetwork().create(ApiService::class.java)
    }
}