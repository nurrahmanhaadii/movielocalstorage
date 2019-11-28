package id.haadii.moviecataloguelocalstorage.widget

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import id.haadii.moviecataloguelocalstorage.R

/**
 */
class MovieCatalogueWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "id.haadii.moviecataloguelocalstorage.TOAST_ACTION"
        const val EXTRA_ITEM = "id.haadii.moviecataloguelocalstorage.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName,
                R.layout.movie_catalogue_widget
            )
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(
                R.id.stack_view,
                R.id.empty_view
            )

            val toastIntent = Intent(context, MovieCatalogueWidget::class.java)
            toastIntent.action =
                TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val toastPendingItem = PendingIntent.getBroadcast(context, 0, toastIntent, FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingItem)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val appWidget = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
                val componentName = ComponentName(context, MovieCatalogueWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,
                    R.id.stack_view
                )
            }
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}