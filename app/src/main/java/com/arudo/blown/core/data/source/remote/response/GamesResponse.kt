package com.arudo.blown.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(

    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int,

    @field:SerializedName("rating")
    val rating: Int,

    @field:SerializedName("metacritic")
    val metacritic: Int,

    @field:SerializedName("playtime")
    val playtime: Int,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("rating_top")
    val ratingTop: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("released")
    val released: String
)
