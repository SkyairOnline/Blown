package com.arudo.blown.core.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.databinding.ItemSmallGameBinding
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.backgroundImageContainer

class SearchAdapter : PagingDataAdapter<Games, SearchAdapter.SearchViewHolder>(itemComparator) {
    private val searchGamesData = ArrayList<Games>()
    var onClickListenerItem: ((Int) -> Unit)? = null

    inner class SearchViewHolder(val itemSearchGameBinding: ItemSmallGameBinding) :
        RecyclerView.ViewHolder(itemSearchGameBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder = SearchViewHolder(
        ItemSmallGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        with(holder) {
            getItem(position)?.let { games ->
                val contextHolder = holder.itemView.context
                backgroundImageContainer(
                    contextHolder,
                    null,
                    games.backgroundImage,
                    itemSearchGameBinding.backgroundImage
                )
                itemSearchGameBinding.txtTitleSearchGame.text = games.name
                holder.itemView.setOnClickListener {
                    onClickListenerItem?.invoke(games.gamesId)
                }
            }
        }
    }

    override fun getItemCount(): Int = searchGamesData.size

    companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<Games>() {
            override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem.gamesId == newItem.gamesId

            override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem == newItem
        }
    }
}