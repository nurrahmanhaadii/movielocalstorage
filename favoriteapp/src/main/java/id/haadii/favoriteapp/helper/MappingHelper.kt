package id.haadii.favoriteapp.helper

import android.database.Cursor
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.BACKDROP
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.POPULARITY
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.POSTER
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.RELEASE
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.TITLE
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion.VOTE
import id.haadii.favoriteapp.db.DatabaseContract.MovieColumns.Companion._ID
import id.haadii.favoriteapp.movie.DataItemMovie

object MappingHelper {

    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<DataItemMovie> {
        val moviesList = ArrayList<DataItemMovie>()
        moviesCursor.moveToFirst()
        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
            val release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW))
            val vote = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTE))
            val popularity = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POPULARITY))
            val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
            val backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP))
            moviesList.add(DataItemMovie(id, title, release, overview, vote, popularity, poster, backdrop))
        }

        return moviesList
    }

    fun mapCursorToObject(moviesCursor: Cursor): DataItemMovie {
        moviesCursor.moveToNext()
        val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID))
        val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
        val release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE))
        val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW))
        val vote = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTE))
        val popularity = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POPULARITY))
        val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
        val backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP))
        return DataItemMovie(id, title, release, overview, vote, popularity, poster, backdrop)
    }
}