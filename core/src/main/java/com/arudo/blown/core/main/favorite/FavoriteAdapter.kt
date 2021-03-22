package com.arudo.blown.core.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.databinding.ItemSmallGameBinding
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.backgroundImageContainer

class FavoriteAdapter : PagingDataAdapter<Games, FavoriteAdapter.FavoriteViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Games>() {
            override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean {
                return oldItem.gamesId == newItem.gamesId
            }

            override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean {
                return oldItem == newItem
            }

        }
    }

    var onClickListenerItem: ((Int) -> Unit)? = null

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
                    backgroundImageContainer(
                        contextHolder,
                        null,
                        backgroundImage,
                        itemSearchGameBinding.backgroundImage
                    )
                    itemSearchGameBinding.txtTitleSearchGame.text = name
                    holder.itemView.setOnClickListener {
                        onClickListenerItem?.invoke(gamesId)
                    }
                }
            }
        }
    }

    fun getSwipedData(favoriteGamesPosition: Int): Games? = getItem(favoriteGamesPosition)

}