package id.haadii.moviecataloguelocalstorage.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.haadii.moviecataloguelocalstorage.R

class SettingActivity : AppCompatActivity() {

    companion object {
        private const val JOB_ID = 10
    }

//    private lateinit var alarmReceiver: AlarmReceiver
//    private lateinit var releaseReceiver: FilmReleaseReceriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingFragment()).commit()

//        alarmReceiver = AlarmReceiver()
//        releaseReceiver = FilmReleaseReceriver()

//        switch_release.setOnClickListener {
//            if (switch_release.isChecked) {
//                Log.e("kfjlakjf", "true")
//                releaseReceiver.setRepeatingAlarm(this)
//            } else {
//                releaseReceiver.cancelAlarm(this)
//            }
//        }
//
//        switch_daily.setOnClickListener {
//            if (switch_daily.isChecked) {
//                alarmReceiver.setRepeatingAlarm(this, "Catalogue movie missing you")
//            } else {
//                alarmReceiver.cancelAlarm(this)
//            }
//        }
    }

//    private fun startJob() {
//        if (isJobRunning(this)) {
//            Toast.makeText(this, "Job service is already scheduled", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val serviceComponent = ComponentName(this, GetMovieReleaseService::class.java)
//
//        val builder = JobInfo.Builder(JOB_ID, serviceComponent)
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//        builder.setRequiresDeviceIdle(false)
//        builder.setRequiresCharging(false)
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder.setPeriodic(900000)
//        } else {
//            builder.setPeriodic(180000)
//        }
//        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//        scheduler.schedule(builder.build())
//        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun cancelJob() {
//        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//        scheduler.cancel(JOB_ID)
//        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun isJobRunning(context: Context) : Boolean {
//        var isScheduled = false
//
//        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
//
//        for (jobInfo in scheduler.allPendingJobs) {
//            if (jobInfo.id == JOB_ID) {
//                isScheduled = true
//                break
//            }
//        }
//        return isScheduled
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
