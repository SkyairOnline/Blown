package com.arudo.blown.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.source.local.entity.FavoriteGamesEntity
import com.arudo.blown.core.source.local.entity.GamesEntity
import com.arudo.blown.core.source.remote.response.DetailGamesResponse
import com.arudo.blown.core.source.remote.response.GamesResponse

object DataMapper {

    fun mapListGamesResponseToEntities(input: List<GamesResponse>): List<GamesEntity> = input.map {
        GamesEntity(
            suggestionsCount = it.suggestionsCount,
            rating = it.rating,
            gamesId = it.gamesId,
            backgroundImage = it.backgroundImage,
            description = null,
            reviewsTextCount = null,
            name = it.name,
            playtime = it.playtime,
            ratingTop = null,
            released = it.released,
            website = null,
            added = it.added
        )
    }

    fun mapListGamesEntitiesToDomain(input: PagingData<GamesEntity>): PagingData<Games> =
        input.map {
            Games(
                suggestionsCount = it.suggestionsCount,
                rating = it.rating,
                gamesId = it.gamesId,
                backgroundImage = it.backgroundImage,
                description = it.description,
                reviewsTextCount = it.reviewsTextCount,
                name = it.name,
                playtime = it.playtime,
                ratingTop = it.ratingTop,
                released = it.released,
                website = it.website,
                added = it.added
            )
        }

    fun mapGamesResponseToEntities(input: DetailGamesResponse): GamesEntity = GamesEntity(
        suggestionsCount = input.suggestionsCount,
        rating = input.rating,
        gamesId = input.gamesId,
        backgroundImage = input.backgroundImage,
        description = input.description,
        reviewsTextCount = input.reviewsTextCount,
        name = input.name,
        playtime = input.playtime,
        ratingTop = input.ratingTop,
        released = input.released,
        website = input.website,
        added = input.added
    )

    fun mapGamesEntitiesToDomain(input: GamesEntity): Games = Games(
        suggestionsCount = input.suggestionsCount,
        rating = input.rating,
        gamesId = input.gamesId,
        backgroundImage = input.backgroundImage,
        description = input.description,
        reviewsTextCount = input.reviewsTextCount,
        name = input.name,
        playtime = input.playtime,
        ratingTop = input.ratingTop,
        released = input.released,
        website = input.website,
        added = input.added
    )

    fun mapListFavoriteGamesEntitiesToDomain(input: List<FavoriteGamesEntity>): List<FavoriteGames> =
        input.map {
            FavoriteGames(
                gamesId = it.gamesId,
                backgroundImage = it.backgroundImage,
                name = it.name
            )
        }


    fun mapFavoriteGamesEntitiesToDomain(input: FavoriteGamesEntity?): FavoriteGames? {
        return if (input != null) {
            FavoriteGames(
                gamesId = input.gamesId,
                backgroundImage = input.backgroundImage,
                name = input.name
            )
        } else {
            null
        }

    }
}