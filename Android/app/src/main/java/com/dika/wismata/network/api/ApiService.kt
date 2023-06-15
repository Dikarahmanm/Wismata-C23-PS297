package com.dika.wismata.network.api

import com.dika.wismata.network.model.DetailWisataModel
import com.dika.wismata.network.model.Register
import com.dika.wismata.network.model.WisataMain

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users")
    fun userRegister(
        @Body user: Map<String, String>
    ): Call<Register>

    @GET("wisata/{id}")
    fun getWisataById(
        @Path("id") id: String
    ): Call<DetailWisataModel>

    @GET("recommendation")
    fun getRecomWisata(): Call<WisataMain>
}