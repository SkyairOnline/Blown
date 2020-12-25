package com.arudo.blown.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlatformsResponse(

    @field:SerializedName("requirements")
    val requirements: RequirementsResponse? = null,

    @field:SerializedName("released_at")
    val releasedAt: String? = null,

    @field:SerializedName("platform")
    val platform: Platform
) : Parcelable
