package com.dika.wismata.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wisata(

    @field:SerializedName("deskripsiWisata")
    val deskripsiWisata: String? = null,

    @field:SerializedName("kategoriWisata")
    val kategoriWisata: String? = null,

    @field:SerializedName("kota")
    val kota: String? = null,

    @field:SerializedName("harga")
    val harga: String? = null,

    @field:SerializedName("idWisata")
    val idWisata: String? = null,

    @field:SerializedName("namaWisata")
    val namaWisata: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("fotoWisata")
    val fotoWisata: String? = null
) : Parcelable