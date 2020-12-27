package com.arudo.blown.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gamesEntity")
data class GamesEntity(

    @NonNull
    val suggestionsCount: Int,

    @NonNull
    val rating: Int,

    @NonNull
    val metacritic: Int,

    @NonNull
    val playtime: Int,

    @NonNull
    val backgroundImage: String,

    @NonNull
    val ratingTop: Int,

    @NonNull
    val name: String,

    @PrimaryKey
    @NonNull
    val id: Int,

    @NonNull
    val released: String,

    @Nullable
    val description: String?,

    @Nullable
    val website: String?,

    @Nullable
    val backgroundImageAdditional: String?,
)