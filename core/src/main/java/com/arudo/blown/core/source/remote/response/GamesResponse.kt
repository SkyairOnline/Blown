package com.arudo.blown.core.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(

    @field:SerializedName("suggestions_count")
    val suggestionsCount: Int,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("playtime")
    val playtime: Int,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val gamesId: Int,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("added")
    val added: Int
)
