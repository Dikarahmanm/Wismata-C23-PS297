package com.dika.wismata.utils

import android.content.Context
import com.dika.wismata.network.api.ApiConfig
import com.dika.wismata.network.database.Repository
import com.dika.wismata.ui.dataStore

object Injection {
    fun providerRepo(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val preference = PreferenceManager.getInstance(context.dataStore)
//        val database = WisataDatabase.getDatabase(context)
        return Repository.getInstance(apiService, preference)
    }
}