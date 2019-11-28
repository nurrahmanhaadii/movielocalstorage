package id.haadii.moviecataloguelocalstorage.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_items")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "first_air_date") var first_air_date: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "vote_count") var vote_count: String,
    @ColumnInfo(name = "popularity") var popularity: String,
    @ColumnInfo(name = "poster_path") var poster_path: String,
    @ColumnInfo(name = "backdrop_path") var backdrop_path: String
)