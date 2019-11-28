package id.haadii.favoriteapp.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemMovie(
    val id: Int,
    val title: String,
    val release_date: String,
    val overview: String,
    val vote_count: String,
    val popularity: String,
    val poster_path: String,
    val backdrop_path: String
) : Parcelable