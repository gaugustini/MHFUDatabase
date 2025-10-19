package com.gaugustini.mhfudatabase.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {

    private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    private val THEME_MODE = intPreferencesKey("theme_mode")

    suspend fun isFirstLaunch(context: Context): Boolean {
        val firstLaunch = context.dataStore.data.map { it[FIRST_LAUNCH] ?: true }.first()
        return firstLaunch
    }

    suspend fun setFirstLaunchDone(context: Context) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_LAUNCH] = false
        }
    }

    fun getThemeMode(context: Context): Flow<ThemeMode> {
        return context.dataStore.data.map { prefs ->
            ThemeMode.fromValue(prefs[THEME_MODE] ?: ThemeMode.SYSTEM.value)
        }
    }

    suspend fun setThemeMode(context: Context, themeMode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE] = themeMode.value
        }
    }

}

enum class ThemeMode(val value: Int) {
    SYSTEM(0),
    LIGHT(1),
    DARK(2);

    companion object {
        fun fromValue(value: Int): ThemeMode = entries.getOrElse(value) { SYSTEM }
    }
}
