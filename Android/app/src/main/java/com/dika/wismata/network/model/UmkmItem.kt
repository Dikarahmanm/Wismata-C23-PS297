package com.dika.wismata.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UmkmItem(

    @field:SerializedName("ownerUmkm")
    val ownerUmkm: String? = null,

    @field:SerializedName("idumkm")
    val idumkm: Int? = null,

    @field:SerializedName("kategoriUmkm")
    val kategoriUmkm: String? = null,

    @field:SerializedName("telpUmkm")
    val telpUmkm: String? = null,

    @field:SerializedName("idWisata")
    val idWisata: String? = null,

    @field:SerializedName("namaUmkm")
    val namaUmkm: String? = null,

    @field:SerializedName("fotoUmkm")
    val fotoUmkm: String? = null,

    @field:SerializedName("deskripsiUmkm")
    val deskripsiUmkm: String? = null
) : Parcelable