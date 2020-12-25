package com.arudo.blown.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamePlatformMetacriticResponse(

    @field:SerializedName("metascore")
    val metascore: Int,

    @field:SerializedName("url")
    val url: String
) : Parcelable