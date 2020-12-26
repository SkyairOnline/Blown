package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlatformEntity(

    @Embedded
    @Nullable
    val requirements: RequirementsEntity,

    @Nullable
    val releasedAt: String,

    @Embedded
    @Nullable
    val platform: DetailPlatformEntity
) : Parcelable