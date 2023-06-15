package com.dika.wismata.network.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailWisataModel(

	@field:SerializedName("wisata")
	val wisata: Wisata? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("umkm")
	val umkm: List<UmkmItem?>? = null
) : Parcelable
