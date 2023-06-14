package com.dika.wismata.network.api

import com.dika.wismata.network.model.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun userRegister(
        @Body user: Map<String, String>
    ): Call<Register>
}