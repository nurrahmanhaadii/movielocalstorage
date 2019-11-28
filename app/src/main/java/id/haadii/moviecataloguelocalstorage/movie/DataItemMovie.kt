package id.haadii.moviecataloguelocalstorage.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemMovie(
    var id: Int = 0,
    var title: String = "",
    var release_date: String = "",
    var overview: String = "",
    var vote_count: String = "",
    var popularity: String = "",
    var poster_path: String = "",
    var backdrop_path: String = ""
) : Parcelable