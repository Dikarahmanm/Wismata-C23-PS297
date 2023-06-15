package com.dika.wismata.network.database

import com.dika.wismata.network.api.ApiService
import com.dika.wismata.network.model.Register
import com.dika.wismata.utils.PreferenceManager
import retrofit2.Call

class Repository(
    private val apiService: ApiService,
    private val preferenceManager: PreferenceManager,
//    private val database: WisataDatabase
) {
    fun userRegister(
        email: String,
        namaDepan: String,
        namaBelakang: String,
        password: String,
        role: String
    ): Call<Register> {
        val user: Map<String, String> = mapOf(
            "email" to email,
            "namaDepan" to namaDepan,
            "namaBelakang" to namaBelakang,
            "password" to password,
            "role" to role
        )
        return apiService.userRegister(user)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: ApiService,
            preferenceManager: PreferenceManager,
//            database: WisataDatabase
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, preferenceManager).also { instance = it }
            }
    }
}