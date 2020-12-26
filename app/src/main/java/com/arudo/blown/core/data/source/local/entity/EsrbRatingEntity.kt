package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "esrbRatingEntity")
@Parcelize
data class EsrbRatingEntity(

    @NonNull
    val name: String,

    @PrimaryKey
    @NonNull
    val id: Int,

    @NonNull
    val slug: String
) : Parcelable