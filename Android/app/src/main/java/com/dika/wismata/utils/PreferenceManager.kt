package com.dika.wismata.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


class PreferenceManager private constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {

        @Volatile
        private var INSTANCE: PreferenceManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferenceManager {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferenceManager(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}