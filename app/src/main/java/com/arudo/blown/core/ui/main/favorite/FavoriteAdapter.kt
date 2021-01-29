package com.arudo.blown.core.ui.main.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.ui.detail.DetailActivity
import com.arudo.blown.core.utils.BackgroundImageContainer
import com.arudo.blown.databinding.ItemSmallGameBinding

class FavoriteAdapter internal constructor() :
    PagedListAdapter<Games, FavoriteAdapter.FavoriteViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Games>() {
            override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class FavoriteViewHolder(val itemSearchGameBinding: ItemSmallGameBinding) :
        RecyclerView.ViewHolder(itemSearchGameBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder = FavoriteViewHolder(
        ItemSmallGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val gamesFavorite = getItem(position)
        gamesFavorite?.let {
            with(holder) {
                with(it) {
                    val contextHolder = holder.itemView.context
                    BackgroundImageContainer(
                        contextHolder,
                        null,
                        backgroundImage,
                        itemSearchGameBinding.backgroundImage
                    )
                    itemSearchGameBinding.txtTitleSearchGame.text = name
                    holder.itemView.setOnClickListener {
                        val intent = Intent(contextHolder, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_DETAIL, id)
                        contextHolder.startActivity(intent)
                    }
                }
            }
        }
    }

    fun getSwipedData(favoriteGamesPosition: Int): Games? = getItem(favoriteGamesPosition)

}