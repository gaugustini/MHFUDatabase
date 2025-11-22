package com.gaugustini.mhfudatabase

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.gaugustini.mhfudatabase.data.UserPreferences
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class MHFUDatabaseApplication : Application() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate() {
        super.onCreate()
        applyInitialLocale()
    }

    private fun applyInitialLocale() {
        val langTag = runBlocking {
            userPreferences.getLanguage().first().code
        }

        val appLocales = LocaleListCompat.forLanguageTags(langTag)
        AppCompatDelegate.setApplicationLocales(appLocales)
    }

}
