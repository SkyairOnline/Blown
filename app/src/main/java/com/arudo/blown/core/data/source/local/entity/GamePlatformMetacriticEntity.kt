package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gamePlatformMetacriticEntity")
@Parcelize
data class GamePlatformMetacriticEntity(

    @NonNull
    val metascore: Int,

    @NonNull
    val url: String
) : Parcelable