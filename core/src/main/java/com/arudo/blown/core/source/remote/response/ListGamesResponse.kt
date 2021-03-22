package com.arudo.blown.core.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListGamesResponse(

    @field:SerializedName("results")
    val results: List<GamesResponse>,

    @field:SerializedName("next")
    val next: String?,

    @field:SerializedName("prev")
    val prev: String?,
)