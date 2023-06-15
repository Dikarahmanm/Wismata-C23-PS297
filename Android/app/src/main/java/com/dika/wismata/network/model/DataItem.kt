package com.dika.wismata.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(

    @field:SerializedName("idWisata")
    val idWisata: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("namaWisata")
    val namaWisata: String? = null,

    @field:SerializedName("fotoWisata")
    val fotoWisata: String? = null
) : Parcelable
