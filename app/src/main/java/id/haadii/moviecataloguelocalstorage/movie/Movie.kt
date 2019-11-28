package id.haadii.moviecataloguelocalstorage.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Movie(
    val results: ArrayList<DataItemMovie>
)