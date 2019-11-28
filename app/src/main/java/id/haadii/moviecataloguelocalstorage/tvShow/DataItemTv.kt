package id.haadii.moviecataloguelocalstorage.tvShow

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemTv (
    val id: Int,
    val name: String,
    val first_air_date: String,
    val overview: String,
    val vote_count: String,
    val popularity: String,
    val poster_path: String,
    val backdrop_path: String
) : Parcelable