package com.arudo.blown.core.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.R
import com.arudo.blown.core.databinding.ItemGameBinding
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.backgroundImageContainer

class HomeAdapter : PagingDataAdapter<Games, HomeAdapter.HomeViewHolder>(itemComparator) {
    private val gamesData = ArrayList<Games>()
    var onClickListenerItem: ((Int) -> Unit)? = null

    inner class HomeViewHolder(val itemGameBinding: ItemGameBinding) :
        RecyclerView.ViewHolder(itemGameBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder) {
            getItem(position)?.let { games ->
                val contextHolder = holder.itemView.context
                val contentItemGameBinding = itemGameBinding.contentItemGame
                backgroundImageContainer(
                    contextHolder,
                    null,
                    games.backgroundImage,
                    itemGameBinding.backgroundImage
                )
                contentItemGameBinding.txtGameTitle.text = games.name
                contentItemGameBinding.txtReleaseDate.text = games.released
                contentItemGameBinding.txtPlaytime.text = contextHolder.getString(
                    R.string.playtimeNumber, games.playtime
                )
                contentItemGameBinding.txtSuggested.text =
                    contextHolder.getString(R.string.suggested_byNumber, games.suggestionsCount)
                contentItemGameBinding.txtRating.text =
                    contextHolder.getString(R.string.ratingNumber, games.rating)
                holder.itemView.setOnClickListener {
                    onClickListenerItem?.invoke(games.gamesId)
                }
            }
        }
    }

    override fun getItemCount(): Int = gamesData.size

    companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<Games>() {
            override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem.gamesId == newItem.gamesId

            override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem == newItem
        }
    }

}