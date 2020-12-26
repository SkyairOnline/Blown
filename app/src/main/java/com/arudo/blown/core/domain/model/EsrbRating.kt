package com.arudo.blown.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EsrbRating(
    val name: String,
    val id: Int,
    val slug: String
) : Parcelable
