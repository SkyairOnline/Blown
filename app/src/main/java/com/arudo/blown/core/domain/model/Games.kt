package com.arudo.blown.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games(
    val added: Int,
    val suggestionsCount: Int,
    val rating: Number,
    val metacritic: Int,
    val playtime: Int,
    val platforms: List<Platform>,
    val backgroundImage: String,
    val tba: Boolean,
    val esrbRating: EsrbRating? = null,
    val ratingTop: Int,
    val reviewsTextCount: String,
    val name: String,
    val id: Int,
    val ratingsCount: Int,
    val updated: String,
    val slug: String,
    val released: String,
    val nameOriginal: String? = null,
    val description: String? = null,
    val gameSeriesCount: Int? = null,
    val metacriticUrl: String? = null,
    val alternativeNames: List<String?>? = null,
    val parentsCount: Int? = null,
    val metacriticPlatforms: List<GamePlatformMetacritic?>? = null,
    val creatorsCount: Int? = null,
    val achievementsCount: Int? = null,
    val redditUrl: String? = null,
    val redditName: String? = null,
    val redditCount: Int? = null,
    val parentAchievementsCount: String? = null,
    val website: String? = null,
    val youtubeCount: String? = null,
    val additionsCount: Int? = null,
    val moviesCount: Int? = null,
    val twitchCount: String? = null,
    val backgroundImageAdditional: String? = null,
    val screenshotsCount: Int? = null,
    val redditDescription: String? = null,
    val redditLogo: String? = null
) : Parcelable
