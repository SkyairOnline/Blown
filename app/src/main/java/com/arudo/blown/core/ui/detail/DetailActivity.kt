package com.arudo.blown.core.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arudo.blown.R
import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.utils.BackgroundImageContainer
import com.arudo.blown.core.utils.Status
import com.arudo.blown.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private var buttonFavorite: Boolean = false
    private var gameName: String = ""

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        activityDetailBinding.progressBarDetail.visibility = View.VISIBLE
        activityDetailBinding.notificationErrorDetail.visibility = View.GONE
        activityDetailBinding.fragmentDetail.visibility = View.GONE
        val gamesId = intent.extras?.getInt(EXTRA_DETAIL) ?: return
        val contentItemDetail = activityDetailBinding.contentItemDetailGame
        detailViewModel.setGameDetailId(gamesId)
        detailViewModel.games.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        statusLayoutVisibility(Status.Loading)
                    }
                    is Resource.Success -> {
                        gameName = it.data!!.name
                        BackgroundImageContainer(
                            null,
                            this,
                            it.data.backgroundImage,
                            activityDetailBinding.backgroundImage
                        )
                        contentItemDetail.txtDescription.text = it.data.description
                        contentItemDetail.txtGameTitleDetail.text = it.data.name
                        contentItemDetail.txtPlayTimeDetail.text =
                            getString(R.string.playtimeNumber, it.data.playtime)
                        contentItemDetail.txtRatingDetail.text = getString(
                            R.string.ratingNumberDetail,
                            it.data.rating,
                            it.data.ratingTop
                        )
                        contentItemDetail.txtReleaseDateDetail.text = it.data.released
                        contentItemDetail.txtReviewDetail.text =
                            getString(R.string.reviewNumber, it.data.reviewsTextCount)
                        contentItemDetail.txtSuggestionDetail.text =
                            getString(R.string.suggested_byNumberDetail, it.data.suggestionsCount)
                        contentItemDetail.txtWebsiteDetail.text = it.data.website
                        statusLayoutVisibility(Status.Success)
                    }
                    is Resource.Error -> {
                        statusLayoutVisibility(Status.Error)
                    }
                }
            }
        })
        detailViewModel.favoriteGames(gamesId).observe(this, {
            if (it != null) {
                buttonFavorite = it.id == gamesId
            }
        })
        contentItemDetail.buttonFavoriteDetail.text = statusButtonText(buttonFavorite)
        contentItemDetail.buttonFavoriteDetail.setOnClickListener {
            buttonFavorite = !buttonFavorite
            statusButtonText(buttonFavorite)
            if (buttonFavorite) {
                detailViewModel.setGameDetailId(gamesId)
                toastMessage(it.context, getString(R.string.added_favorite_toast, gameName))
            } else {
                detailViewModel.deleteFavoriteGames(gamesId)
                toastMessage(it.context, getString(R.string.remove_favorite_toast, gameName))
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

    private fun toastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun statusLayoutVisibility(status: Status) {
        activityDetailBinding.progressBarDetail.visibility = View.GONE
        activityDetailBinding.notificationErrorDetail.visibility = View.GONE
        activityDetailBinding.fragmentDetail.visibility = View.GONE

        when (status) {
            Status.Success -> {
                activityDetailBinding.fragmentDetail.visibility = View.VISIBLE
            }
            Status.Error -> {
                activityDetailBinding.notificationErrorDetail.visibility = View.VISIBLE
            }
            Status.Loading -> {
                activityDetailBinding.progressBarDetail.visibility = View.VISIBLE
            }
        }
    }
}