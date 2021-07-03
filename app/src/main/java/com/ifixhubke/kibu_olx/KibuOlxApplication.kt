package com.ifixhubke.kibu_olx

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class KibuOlxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())

        val sharedPreferences = getSharedPreferences("ui_mode", MODE_PRIVATE)
        val itemUIMode = sharedPreferences.getBoolean("ISCHECKED", false)

        uiMode(itemUIMode)
    }

    private fun uiMode(isDarkModeOn: Boolean) {
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}