package com.arudo.blown.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Platform(
    val requirements: Requirements? = null,
    val releasedAt: String? = null,
    val platform: DetailPlatform
) : Parcelable
