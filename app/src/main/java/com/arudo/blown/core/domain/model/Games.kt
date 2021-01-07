package com.arudo.blown.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games(
    val suggestionsCount: Int,
    val rating: Double,
    val reviewsTextCount: Int? = null,
    val playtime: Int,
    val backgroundImage: String,
    val ratingTop: Int? = null,
    val name: String,
    val id: Int,
    val released: String,
    val description: String? = null,
    val website: String? = null,
    val backgroundImageAdditional: String? = null,
) : Parcelable
