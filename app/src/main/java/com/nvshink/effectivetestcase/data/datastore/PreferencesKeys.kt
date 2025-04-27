package com.nvshink.effectivetestcase.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val ONBOARDING_SHOWN = booleanPreferencesKey("onboarding_shown")
    val AUTHORIZED = booleanPreferencesKey("authorized")
}

class AppPreferences(private val dataStore: DataStore<Preferences>) {
    val onboardingShown = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.ONBOARDING_SHOWN] ?: false
        }
    suspend fun setOnboardingShown(shown: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_SHOWN] = shown
        }
    }

    val authorized = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTHORIZED] ?: false
        }
    suspend fun setAuthorized(authorized: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTHORIZED] = authorized
        }
    }
}