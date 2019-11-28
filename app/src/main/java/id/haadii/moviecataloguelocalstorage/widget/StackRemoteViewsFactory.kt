package id.haadii.moviecataloguelocalstorage.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.widget.MovieCatalogueWidget.Companion.EXTRA_ITEM
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract.MovieColumns.Companion.POSTER
import id.haadii.moviecataloguelocalstorage.repository.Constants.Companion.IMG_URL
import java.util.concurrent.ExecutionException


class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private lateinit var cursor: Cursor

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()
        cursor = context.contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null) as Cursor
        Binder.restoreCallingIdentity(identityToken)

    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(context.packageName,
            R.layout.item_widget
        )

        if (cursor.moveToPosition(p0)) {
            val movieFavorite = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
            try {
                val bmp = Glide.with(context)
                    .asBitmap()
                    .load(IMG_URL + movieFavorite)
                    .apply(RequestOptions().fitCenter())
                    .submit(512, 512)
                    .get()
                rv.setImageViewBitmap(R.id.imageView, bmp)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
        }

        val extra = bundleOf(EXTRA_ITEM to p0)

        val fillIntent = Intent()
        fillIntent.putExtras(extra)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        try {
            println("Loading view $p0")
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return  rv
    }

    override fun getCount(): Int = cursor.count

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}
