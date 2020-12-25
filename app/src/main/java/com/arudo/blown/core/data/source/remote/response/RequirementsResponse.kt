package com.arudo.blown.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequirementsResponse(

    @field:SerializedName("minimum")
    val minimum: String,

    @field:SerializedName("recommended")
    val recommended: String
) : Parcelable
