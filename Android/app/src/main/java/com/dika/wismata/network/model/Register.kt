package com.dika.wismata.network.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Register(
    @field:SerializedName("message")
    val message: String? = null
) : Parcelable