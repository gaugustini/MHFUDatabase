package com.gaugustini.mhfudatabase.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    private companion object PreferencesKeys {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
        val THEME_MODE = intPreferencesKey("theme_mode")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val LAST_VERSION = intPreferencesKey("last_version")
    }

    suspend fun isFirstLaunch(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[FIRST_LAUNCH] ?: true
        }.first()
    }

    suspend fun setFirstLaunchDone() {
        dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH] = false
        }
    }

    fun getThemeMode(): Flow<ThemeMode> {
        return dataStore.data.map { preferences ->
            ThemeMode.fromValue(preferences[THEME_MODE] ?: ThemeMode.SYSTEM.value)
        }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.value
        }
    }

    fun getLanguage(): Flow<Language> {
        return dataStore.data.map { preferences ->
            Language.fromCode(preferences[APP_LANGUAGE] ?: getDeviceLanguage())
        }
    }

    suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = language.code
        }
    }

    private fun getDeviceLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    suspend fun getLastAppVersion(): Int {
        return dataStore.data.map { preferences ->
            preferences[LAST_VERSION] ?: -1
        }.first()
    }

    suspend fun setLastAppVersion(version: Int) {
        dataStore.edit { preferences ->
            preferences[LAST_VERSION] = version
        }
    }

}

enum class ThemeMode(val value: Int) {
    SYSTEM(0),
    LIGHT(1),
    DARK(2);

    companion object {
        fun fromValue(value: Int): ThemeMode = entries.find { it.value == value } ?: SYSTEM
    }
}

enum class Language(val code: String) {
    ENGLISH("en"),
    SPANISH("es");

    companion object {
        fun fromCode(code: String): Language {
            val base = code.lowercase().substringBefore("-")
            return entries.find { it.code == base } ?: ENGLISH
        }
    }
}
