package com.kangmicin.hotmovie

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import java.util.*

abstract class AppActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = appTitle()
    }

    protected abstract fun appTitle(): String?

    override fun attachBaseContext(newBase: Context?) {
        if (newBase is Context) {
            super.attachBaseContext(updateBaseContextLocal(newBase))
        }else {
            super.attachBaseContext(newBase)
        }
    }

    private fun updateBaseContextLocal(context: Context): Context {
        val manager = PreferenceManager(context)
        val language = manager.sharedPreferences.getString("language", Locale.getDefault().language)

        val mConfiguration = context.resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        mConfiguration.setLocale(locale)

        return context.createConfigurationContext(mConfiguration)
    }
}