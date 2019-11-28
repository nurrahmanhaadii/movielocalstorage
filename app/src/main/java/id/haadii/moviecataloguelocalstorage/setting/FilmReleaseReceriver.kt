package id.haadii.moviecataloguelocalstorage.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.haadii.moviecataloguelocalstorage.BuildConfig
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.movie.DataItemMovie
import id.haadii.moviecataloguelocalstorage.movie.Movie
import id.haadii.moviecataloguelocalstorage.repository.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FilmReleaseReceriver : BroadcastReceiver() {

    companion object {
        const val EXTRA_MESSAGE = "message"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(p0: Context, p1: Intent) {
        Log.e("onreceive", "true")
        val notifId = ID_REPEATING

        getMovieRelease(p0, notifId)

    }

    fun setRepeatingAlarm(context: Context) {
        Log.e("inrepeating", "true")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, FilmReleaseReceriver::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val pendingIntent = PendingIntent.getBroadcast(context,
            ID_REPEATING, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, FilmReleaseReceriver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    private fun getMovieRelease(context: Context, notifId: Int) {
        Log.e("ingetmovierelease", "true")
        val today = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today)
        NetworkConfig().api().releaseMovie(BuildConfig.API_KEY, sdf, sdf).enqueue(object :
            Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val listMovie : ArrayList<DataItemMovie>  = response.body()?.results!!
                    if (response.body() != null) {
                        for (movie in listMovie) {
                            showNotification(context, movie.title, movie.title + " has been released today", movie.id)
                        }
                    }
                } else {
                }
            }
        })
    }

    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        Log.e("shownotif", "true")
        val CHANNEL_ID = "Channel1"
        val CHANNEL_NAME = "Movie release scheduler Channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

}
