package com.arudo.blown.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arudo.blown.R
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.source.local.Resource
import com.arudo.blown.core.utils.Status
import com.arudo.blown.core.utils.backgroundImageContainer
import com.arudo.blown.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val activityDetailBinding get() = _activityDetailBinding!!
    private var buttonFavorite: Boolean = false
    private var gameName: String = ""
    private var favoriteGame: FavoriteGames? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val gamesId = intent.extras?.getInt("extra_detail") ?: return
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        activityDetailBinding.notificationLoadingDetail.root.visibility = View.VISIBLE
        activityDetailBinding.notificationErrorDetail.root.visibility = View.GONE
        activityDetailBinding.fragmentDetail.visibility = View.GONE
        val contentItemDetail = activityDetailBinding.contentItemDetailGame
        detailViewModel.setGameDetailId(gamesId)
        detailViewModel.games.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        statusLayoutVisibility(Status.Loading)
                    }
                    is Resource.Success -> {
                        gameName = it.data?.name ?: ""
                        backgroundImageContainer(
                            null,
                            this,
                            it.data?.backgroundImage ?: "",
                            activityDetailBinding.backgroundImage
                        )
                        contentItemDetail.txtDescription.text = it.data?.description ?: ""
                        contentItemDetail.txtGameTitleDetail.text = it.data?.name ?: ""
                        contentItemDetail.txtPlayTimeDetail.text =
                            getString(R.string.playtimeNumber, it.data?.playtime as Int)
                        contentItemDetail.txtRatingDetail.text = getString(
                            R.string.ratingNumberDetail,
                            it.data?.rating as Double,
                            it.data?.ratingTop as Int
                        )
                        contentItemDetail.txtReleaseDateDetail.text = it.data?.released ?: ""
                        contentItemDetail.txtReviewDetail.text =
                            getString(R.string.reviewNumber, it.data?.reviewsTextCount as Int)
                        contentItemDetail.txtSuggestionDetail.text =
                            getString(
                                R.string.suggested_byNumberDetail,
                                it.data?.suggestionsCount as Int
                            )
                        contentItemDetail.txtWebsiteDetail.text = it.data?.website ?: ""
                        statusLayoutVisibility(Status.Success)
                        favoriteGame =
                            FavoriteGames(gamesId, it.data?.backgroundImage, it.data?.name)
                    }
                    is Resource.Error -> {
                        statusLayoutVisibility(Status.Error)
                    }
                }
            }
        })
        detailViewModel.favoriteGames(gamesId).observe(this, {
            buttonFavorite = it != null && it.gamesId == gamesId
            contentItemDetail.buttonFavoriteDetail.text = statusButtonText(buttonFavorite)
        })
        contentItemDetail.buttonFavoriteDetail.setOnClickListener {
            buttonFavorite = !buttonFavorite
            contentItemDetail.buttonFavoriteDetail.text = statusButtonText(buttonFavorite)
            if (favoriteGame != null) {
                if (buttonFavorite) {
                    detailViewModel.insertFavoriteGames(favoriteGame!!)
                } else {
                    detailViewModel.deleteFavoriteGames(favoriteGame!!)
                }
            }

        }
        setContentView(activityDetailBinding.root)
    }

    private fun statusButtonText(buttonBool: Boolean): String {
        return if (buttonBool) {
            getString(R.string.added_favorite)
        } else {
            getString(R.string.add_favorite)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun statusLayoutVisibility(status: Status) {
        activityDetailBinding.notificationLoadingDetail.root.visibility = View.GONE
        activityDetailBinding.notificationErrorDetail.root.visibility = View.GONE
        activityDetailBinding.fragmentDetail.visibility = View.GONE

        when (status) {
            Status.Success -> {
                activityDetailBinding.fragmentDetail.visibility = View.VISIBLE
            }
            Status.Error -> {
                activityDetailBinding.notificationErrorDetail.root.visibility = View.VISIBLE
            }
            Status.Loading -> {
                activityDetailBinding.notificationLoadingDetail.root.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
        buttonFavorite = false
        gameName = ""
        favoriteGame = null
    }
}