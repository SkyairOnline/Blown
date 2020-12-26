package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "favoriteGamesEntity")
@Parcelize
data class FavoriteGamesEntity(

    @PrimaryKey
    @NonNull
    val id: Int,

    @NonNull
    val createdDate: Date = Date(System.currentTimeMillis())
) : Parcelable