package com.arudo.blown.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamePlatformMetacritic(
    val metascore: Int,
    val url: String
) : Parcelable
