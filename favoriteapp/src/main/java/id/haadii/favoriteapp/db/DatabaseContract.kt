package id.haadii.favoriteapp.db

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseContract {

    companion object {
        const val AUTHORITY = "id.haadii.moviecataloguelocalstorage"
        const val SCHEME = "content"
    }

    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RELEASE = "release_date"
            const val OVERVIEW = "overview"
            const val VOTE = "vote_count"
            const val POPULARITY = "popularity"
            const val POSTER = "poster_path"
            const val BACKDROP = "backdrop_path"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}