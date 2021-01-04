package com.arudo.blown.core.utils

import com.arudo.blown.core.data.source.local.entity.GamesEntity
import com.arudo.blown.core.data.source.remote.response.GamesResponse
import com.arudo.blown.core.domain.model.Games

object DataMapper {

    fun mapGamesResponseToEntities(input: List<GamesResponse>): List<GamesEntity> = input.map {
        GamesEntity(
            suggestionsCount = it.suggestionsCount,
            rating = it.rating,
            id = it.id,
            backgroundImage = it.backgroundImage,
            backgroundImageAdditional = null,
            description = null,
            reviewsTextCount = it.reviewsTextCount,
            name = it.name,
            playtime = it.playtime,
            ratingTop = it.ratingTop,
            released = it.released,
            website = null
        )
    }

    fun mapGamesEntitiesToDomain(input: List<GamesEntity>): List<Games> = input.map {
        Games(
            suggestionsCount = it.suggestionsCount,
            rating = it.rating,
            id = it.id,
            backgroundImage = it.backgroundImage,
            backgroundImageAdditional = it.backgroundImageAdditional,
            description = it.description,
            reviewsTextCount = it.reviewsTextCount,
            name = it.name,
            playtime = it.playtime,
            ratingTop = it.ratingTop,
            released = it.released,
            website = it.website
        )
    }
}