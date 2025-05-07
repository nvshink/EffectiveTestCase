package com.nvshink.effectivetestcase.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val ONBOARDING_IS_SHOW = booleanPreferencesKey("onboarding_is_show")
    val AUTHORIZED = booleanPreferencesKey("authorized")
}

class AppPreferences(private val dataStore: DataStore<Preferences>) {
    val onboardingIsShow = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.ONBOARDING_IS_SHOW] ?: true
        }
    suspend fun setOnboardingIsShow(isShow: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_IS_SHOW] = isShow
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