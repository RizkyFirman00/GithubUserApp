package com.example.submission1.localData

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {
    private val settingsDataStore = context.prefDataStore
    private val key = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> =
        settingsDataStore.data.map { preferences -> preferences[key] ?: false }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingsDataStore.edit { preferences -> preferences[key] = isDarkModeActive }
    }
}