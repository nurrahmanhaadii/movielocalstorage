package id.haadii.moviecataloguelocalstorage.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.BACKDROP
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.POPULARITY
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.POSTER
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.RELEASE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.TABLE_NAME
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.TITLE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.VOTE
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion._ID
import id.haadii.moviecataloguelocalstorage.movie.DataItemMovie

class MovieHelper (context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private lateinit var database: SQLiteDatabase

        private var INSTANCE: MovieHelper? = null
        fun getInstance(context: Context): MovieHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun getAllMovie(): ArrayList<DataItemMovie> {
        val arrayList = ArrayList<DataItemMovie>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
            "$_ID ASC", null)
        cursor.moveToFirst()
        var movie: DataItemMovie
        if (cursor.count > 0) {
            do {
                movie = DataItemMovie()
                movie.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                movie.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
                movie.release_date = cursor.getString(cursor.getColumnIndex(RELEASE))
                movie.overview = cursor.getString(cursor.getColumnIndex(OVERVIEW))
                movie.vote_count = cursor.getString(cursor.getColumnIndex(VOTE))
                movie.popularity = cursor.getString(cursor.getColumnIndex(POPULARITY))
                movie.poster_path = cursor.getString(cursor.getColumnIndex(POSTER))
                movie.backdrop_path = cursor.getString(cursor.getColumnIndex(BACKDROP))

                arrayList.add(movie)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insertMovie(movie: DataItemMovie): Long {
        val args = ContentValues()
        args.put(_ID, movie.id)
        args.put(TITLE, movie.title)
        args.put(RELEASE, movie.release_date)
        args.put(OVERVIEW, movie.overview)
        args.put(VOTE, movie.vote_count)
        args.put(POPULARITY, movie.popularity)
        args.put(POSTER, movie.poster_path)
        args.put(BACKDROP, movie.backdrop_path)
        return database.insert(DATABASE_TABLE, null, args)
    }

    fun updateMovie(movie: DataItemMovie): Int {
        val args = ContentValues()
        args.put(_ID, movie.id)
        args.put(TITLE, movie.title)
        args.put(RELEASE, movie.release_date)
        args.put(OVERVIEW, movie.overview)
        args.put(VOTE, movie.vote_count)
        args.put(POPULARITY, movie.popularity)
        args.put(POSTER, movie.poster_path)
        args.put(BACKDROP, movie.backdrop_path)
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.id + "'", null)
    }

    fun deleteNote(id: Int): Int {
        return database.delete(TABLE_NAME, "$_ID = '$id'", null)
    }

}