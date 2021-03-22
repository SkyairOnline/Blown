package com.arudo.blown.core.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteGamesEntity")
data class FavoriteGamesEntity(

    @PrimaryKey
    @NonNull
    val gamesId: Int,

    @Nullable
    val backgroundImage: String?,

    @Nullable
    val name: String?,
)