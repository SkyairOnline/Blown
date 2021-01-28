package com.arudo.blown.core.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.R
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.ui.detail.DetailActivity
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
                val contentItemGameBinding = itemGameBinding.contentItemGame
                BackgroundImageContainer(
                    contextHolder,
                    null,
                    backgroundImage,
                    itemGameBinding.backgroundImage
                )
                contentItemGameBinding.txtGameTitle.text = name
                contentItemGameBinding.txtReleaseDate.text = released
                contentItemGameBinding.txtPlaytime.text = contextHolder.getString(
                    R.string.playtimeNumber, playtime
                )
                contentItemGameBinding.txtSuggested.text =
                    contextHolder.getString(R.string.suggested_byNumber, suggestionsCount)
                contentItemGameBinding.txtRating.text =
                    contextHolder.getString(R.string.ratingNumber, rating)
                holder.itemView.setOnClickListener {
                    val intent = Intent(contextHolder, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, id)
                    contextHolder.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = gamesData.size

}