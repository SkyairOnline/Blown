package com.arudo.blown.core.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKeys")
data class RemotePageKeysEntity(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)