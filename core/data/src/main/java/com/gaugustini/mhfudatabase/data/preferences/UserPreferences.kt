package com.gaugustini.mhfudatabase.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.enums.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

/**
 * This class is used to manage the user preferences.
 */
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object PreferencesKeys {
        val THEME_MODE = intPreferencesKey("theme_mode")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
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

    private fun getDeviceLanguage(): String {
        return Locale.getDefault().toLanguageTag()
    }

    suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = language.code
        }
    }

}
