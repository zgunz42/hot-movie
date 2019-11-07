package com.kangmicin.hotmovie.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.ui.AppActivity
import com.kangmicin.hotmovie.utilities.Ui

class SettingsActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private val listener: SharedPreferences.OnSharedPreferenceChangeListener
            get() {
            return SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == "language") {
                    activity?.let { Ui.updateActivity(it) }
                }
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        }

        override fun onDestroy() {
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
            super.onDestroy()
        }
    }
}