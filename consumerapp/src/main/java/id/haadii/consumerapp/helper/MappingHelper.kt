package id.haadii.consumerapp.helper

import android.database.Cursor
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.BACKDROP
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.POPULARITY
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.POSTER
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.RELEASE
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.TITLE
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion.VOTE
import id.haadii.consumerapp.db.DatabaseContract.MovieColumns.Companion._ID
import id.haadii.consumerapp.model.DataItemMovie

object MappingHelper {

    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<DataItemMovie> {
        val moviesList = ArrayList<DataItemMovie>()
        moviesCursor.moveToFirst()
        do {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
            val release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW))
            val vote = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTE))
            val popularity = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POPULARITY))
            val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
            val backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP))
            moviesList.add(
                DataItemMovie(
                    id,
                    title,
                    release,
                    overview,
                    vote,
                    popularity,
                    poster,
                    backdrop
                )
            )
        } while (moviesCursor.moveToNext()) 

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
        return DataItemMovie(
            id,
            title,
            release,
            overview,
            vote,
            popularity,
            poster,
            backdrop
        )
    }
}