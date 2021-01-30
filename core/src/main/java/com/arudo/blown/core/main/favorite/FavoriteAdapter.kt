package com.arudo.blown.core.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.databinding.ItemSmallGameBinding
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.BackgroundImageContainer

class FavoriteAdapter : PagedListAdapter<Games, FavoriteAdapter.FavoriteViewHolder>(diffCallback) {

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
                    BackgroundImageContainer(
                        contextHolder,
                        null,
                        backgroundImage,
                        itemSearchGameBinding.backgroundImage
                    )
                    itemSearchGameBinding.txtTitleSearchGame.text = name
                    holder.itemView.setOnClickListener {
                        onClickListenerItem?.invoke(id)
                    }
                }
            }
        }
    }

    fun getSwipedData(favoriteGamesPosition: Int): Games? = getItem(favoriteGamesPosition)

}