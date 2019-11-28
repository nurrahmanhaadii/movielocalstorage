package id.haadii.moviecataloguelocalstorage.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbnoteapp"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.MovieColumns._ID} INTEGER PRIMARY KEY," +
                " ${DatabaseContract.MovieColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.RELEASE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.VOTE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.POPULARITY} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.POSTER} TEXT NOT NULL,"+
                " ${DatabaseContract.MovieColumns.BACKDROP} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}