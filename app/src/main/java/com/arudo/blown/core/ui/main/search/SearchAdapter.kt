package com.arudo.blown.core.ui.main.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.core.domain.model.Games
import com.arudo.blown.core.ui.detail.DetailActivity
import com.arudo.blown.core.utils.BackgroundImageContainer
import com.arudo.blown.databinding.ItemSearchGameBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private val searchGamesData = ArrayList<Games>()

    fun setData(data: List<Games>) {
        searchGamesData.clear()
        searchGamesData.addAll(data)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val itemSearchGameBinding: ItemSearchGameBinding) :
        RecyclerView.ViewHolder(itemSearchGameBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder = SearchViewHolder(
        ItemSearchGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        with(holder) {
            with(searchGamesData[position]) {
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

    override fun getItemCount(): Int = searchGamesData.size
}