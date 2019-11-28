package id.haadii.moviecataloguelocalstorage.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import id.haadii.moviecataloguelocalstorage.R

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener  {

    private lateinit var release : SwitchPreference
    private lateinit var daily : SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var releaseReceiver: FilmReleaseReceriver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)

        release = findPreference<SwitchPreference>("switch_release") as SwitchPreference
        daily = findPreference<SwitchPreference>("switch_daily") as SwitchPreference

        alarmReceiver = AlarmReceiver()
        releaseReceiver = FilmReleaseReceriver()

        val sh = preferenceManager.sharedPreferences
        release.isChecked = sh.getBoolean("switch_release", false)
        daily.isChecked = sh.getBoolean("switch_daily", false)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

        if (key == "switch_daily") {
            daily.isChecked = sharedPreferences.getBoolean("switch_daily", false)
            Log.e("daily", "${daily.isChecked}")

            if (daily.isChecked) {
                alarmReceiver.setRepeatingAlarm(activity!!, "Catalogue movie missing you")
            } else {
                alarmReceiver.cancelAlarm(activity!!)
            }
        }

        if (key == "switch_release") {
            release.isChecked = sharedPreferences.getBoolean("switch_release", false)
            Log.e("release", "${release.isChecked}")


            if (release.isChecked) {
                releaseReceiver.setRepeatingAlarm(activity!!)
            } else {
                releaseReceiver.cancelAlarm(activity!!)
            }
        }

    }

        override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}
