package com.arudo.blown.core.utils

import com.arudo.blown.core.data.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.data.source.local.entity.GamesEntity
import com.arudo.blown.core.data.source.remote.response.DetailGamesResponse
import com.arudo.blown.core.data.source.remote.response.GamesResponse
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games

object DataMapper {

    fun mapListGamesResponseToEntities(input: List<GamesResponse>): List<GamesEntity> = input.map {
        GamesEntity(
            suggestionsCount = it.suggestionsCount,
            rating = it.rating,
            id = it.id,
            backgroundImage = it.backgroundImage,
            backgroundImageAdditional = null,
            description = null,
            reviewsTextCount = null,
            name = it.name,
            playtime = it.playtime,
            ratingTop = null,
            released = it.released,
            website = null
        )
    }

    fun mapListGamesEntitiesToDomain(input: List<GamesEntity>): List<Games> = input.map {
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

    fun mapGamesResponseToEntities(input: DetailGamesResponse): GamesEntity = GamesEntity(
        suggestionsCount = input.suggestionsCount,
        rating = input.rating,
        id = input.id,
        backgroundImage = input.backgroundImage,
        backgroundImageAdditional = input.backgroundImageAdditional,
        description = input.description,
        reviewsTextCount = input.reviewsTextCount,
        name = input.name,
        playtime = input.playtime,
        ratingTop = input.ratingTop,
        released = input.released,
        website = input.website
    )

    fun mapGamesEntitiesToDomain(input: GamesEntity): Games = Games(
        suggestionsCount = input.suggestionsCount,
        rating = input.rating,
        id = input.id,
        backgroundImage = input.backgroundImage,
        backgroundImageAdditional = input.backgroundImageAdditional,
        description = input.description,
        reviewsTextCount = input.reviewsTextCount,
        name = input.name,
        playtime = input.playtime,
        ratingTop = input.ratingTop,
        released = input.released,
        website = input.website
    )

    fun mapFavoriteGamesEntitiesToDomain(input: FavoriteGamesEntity): FavoriteGames = FavoriteGames(
        id = input.id
    )
}