package com.arudo.blown.core.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.databinding.ItemSmallGameBinding
import com.arudo.blown.core.domain.model.FavoriteGames
import com.arudo.blown.core.utils.backgroundImageContainer

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val favoriteGameData = ArrayList<FavoriteGames>()
    var onClickListenerItem: ((Int) -> Unit)? = null

    fun setData(data: List<FavoriteGames>) {
        favoriteGameData.clear()
        favoriteGameData.addAll(data)
        notifyDataSetChanged()
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
        with(holder) {
            with(favoriteGameData[position]) {
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

    override fun getItemCount(): Int = favoriteGameData.size

}