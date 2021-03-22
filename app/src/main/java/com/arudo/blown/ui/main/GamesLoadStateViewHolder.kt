package com.arudo.blown.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.R
import com.arudo.blown.databinding.LoadFooterBinding

class GamesLoadStateViewHolder(
    private val loadFooterBinding: LoadFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(loadFooterBinding.root) {
    init {
        loadFooterBinding.retryMsgLoadFooter.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        loadFooterBinding.progressLoadFooter.isVisible = loadState is LoadState.Loading
        loadFooterBinding.retryMsgLoadFooter.isVisible = loadState !is LoadState.Loading
        loadFooterBinding.errorMsgLoadFooter.root.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GamesLoadStateViewHolder {
            return GamesLoadStateViewHolder(
                LoadFooterBinding.bind(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.load_footer, parent, false)
                ),
                retry
            )
        }
    }
}