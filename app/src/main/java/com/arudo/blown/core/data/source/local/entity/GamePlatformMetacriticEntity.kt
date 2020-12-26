package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamePlatformMetacriticEntity(

    @NonNull
    val metascore: Int,

    @NonNull
    val url: String
) : Parcelable