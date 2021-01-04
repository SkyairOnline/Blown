package com.arudo.blown.core.ui.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.R
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.utils.BackgroundImageContainer
import com.arudo.blown.databinding.ItemGameBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private val gamesData = ArrayList<Games>()

    fun setData(data: List<Games>) {
        gamesData.clear()
        gamesData.addAll(data)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(val itemGameBinding: ItemGameBinding) :
        RecyclerView.ViewHolder(itemGameBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder) {
            with(gamesData[position]) {
                val contextHolder = holder.itemView.context
                BackgroundImageContainer(
                    holder.itemView.context,
                    backgroundImage,
                    itemGameBinding.backgroundImage
                )
                itemGameBinding.contentItemGame.txtGameTitle.text = name
                itemGameBinding.contentItemGame.txtReleaseDate.text = released
                itemGameBinding.contentItemGame.txtPlaytime.text = contextHolder.getString(
                    R.string.playtimeNumber, playtime
                )
                itemGameBinding.contentItemGame.txtSuggested.text =
                    contextHolder.getString(R.string.suggested_byNumber, suggestionsCount)
                itemGameBinding.contentItemGame.txtRating.text =
                    contextHolder.getString(R.string.ratingNumber, rating)
            }
        }
    }

    override fun getItemCount(): Int = gamesData.size

}