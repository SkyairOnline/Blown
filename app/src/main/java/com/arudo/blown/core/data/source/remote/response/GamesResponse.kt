package com.arudo.blown.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GamesResponse(

    @field:SerializedName("added")
    val added: Int,

    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int,

    @field:SerializedName("rating")
    val rating: Number,

    @field:SerializedName("metacritic")
    val metacritic: Int,

    @field:SerializedName("playtime")
    val playtime: Int,

    @field:SerializedName("platforms")
    val platforms: List<PlatformResponse>,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("tba")
    val tba: Boolean,

    @field:SerializedName("esrb_rating")
    val esrbRating: EsrbRatingResponse? = null,

    @field:SerializedName("rating_top")
    val ratingTop: Int,

    @field:SerializedName("reviews_text_count")
    val reviewsTextCount: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("ratings_count")
    val ratingsCount: Int,

    @field:SerializedName("updated")
    val updated: String,

    @field:SerializedName("slug")
    val slug: String,

    @field:SerializedName("released")
    val released: String
) : Parcelable
