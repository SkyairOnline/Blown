package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gamesEntity")
@Parcelize
data class GamesEntity(

    @NonNull
    val added: Int,

    @NonNull
    val suggestionsCount: Int,

    @NonNull
    val rating: Int,

    @NonNull
    val metacritic: Int,

    @NonNull
    val playtime: Int,

    @NonNull
    @Expose
    val platforms: List<PlatformEntity>,

    @NonNull
    val backgroundImage: String,

    @NonNull
    val tba: Boolean,

    @NonNull
    val ratingTop: Int,

    @NonNull
    val reviewsTextCount: String,

    @NonNull
    val name: String,

    @PrimaryKey
    @NonNull
    val id: Int,

    @NonNull
    val ratingsCount: Int,

    @NonNull
    val updated: String,

    @NonNull
    val slug: String,

    @NonNull
    val released: String,

    @Nullable
    val nameOriginal: String,

    @Nullable
    val description: String,

    @Nullable
    val gameSeriesCount: Int,

    @Nullable
    val metacriticUrl: String,

    @Nullable
    val parentsCount: Int,

    @Nullable
    @Expose
    val metacriticPlatforms: List<GamePlatformMetacriticEntity>,

    @Nullable
    val creatorsCount: Int,

    @Nullable
    val achievementsCount: Int,

    @Nullable
    val redditUrl: String,

    @Nullable
    val redditName: String,

    @Nullable
    val redditCount: Int,

    @Nullable
    val parentAchievementsCount: String,

    @Nullable
    val website: String,

    @Nullable
    val youtubeCount: String,

    @Nullable
    val additionsCount: Int,

    @Nullable
    val moviesCount: Int,

    @Nullable
    val twitchCount: String,

    @Nullable
    val backgroundImageAdditional: String,

    @Nullable
    val screenshotsCount: Int,

    @Nullable
    val redditDescription: String,

    @Nullable
    val redditLogo: String,
) : Parcelable