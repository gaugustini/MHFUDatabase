package com.gaugustini.mhfudatabase.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {

    private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")

    suspend fun isFirstLaunch(context: Context): Boolean {
        val firstLaunch = context.dataStore.data.map { it[FIRST_LAUNCH] ?: true }.first()
        return firstLaunch
    }

    suspend fun setFirstLaunchDone(context: Context) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_LAUNCH] = false
        }
    }

}
