package com.gaugustini.mhfudatabase.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This class is used to manage the app preferences.
 */
class AppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object PreferencesKeys {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
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
