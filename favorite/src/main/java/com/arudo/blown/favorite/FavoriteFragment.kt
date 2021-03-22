package com.arudo.blown.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.R
import com.arudo.blown.core.main.favorite.FavoriteAdapter
import com.arudo.blown.favorite.databinding.FragmentFavoriteBinding
import com.arudo.blown.favorite.di.favoriteModule
import com.arudo.blown.ui.detail.DetailActivity
import com.arudo.blown.ui.main.GamesLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var favoriteAdapter = FavoriteAdapter()
    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val fragmentFavoriteBinding get() = _fragmentFavoriteBinding!!
    private var listGameFavoriteJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.adapter =
            favoriteAdapter.withLoadStateHeaderAndFooter(
                header = GamesLoadStateAdapter { favoriteAdapter.retry() },
                footer = GamesLoadStateAdapter { favoriteAdapter.retry() }
            )

        fragmentFavoriteBinding.progressBarFavorite.visibility = View.VISIBLE
        fragmentFavoriteBinding.notificationFavoriteStart.root.visibility = View.GONE
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.visibility = View.GONE

        favoriteAdapter.addLoadStateListener {
            fragmentFavoriteBinding.rvHorizontalFavoriteGame.isVisible =
                it.source.refresh is LoadState.NotLoading
            fragmentFavoriteBinding.progressBarFavorite.isVisible =
                it.source.refresh is LoadState.Loading
            fragmentFavoriteBinding.notificationFavoriteStart.root.isVisible =
                it.source.refresh is LoadState.Error
        }

        getListFavoriteGames()
        initListFavoriteGame()
        itemTouchSlider.attachToRecyclerView(fragmentFavoriteBinding.rvHorizontalFavoriteGame)

        favoriteAdapter.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("extra_detail", it)
            startActivity(intent)
        }
    }

    private fun initListFavoriteGame() {
        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            favoriteAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { fragmentFavoriteBinding.rvHorizontalFavoriteGame.scrollToPosition(0) }
        }
    }

    private fun getListFavoriteGames() {
        listGameFavoriteJob?.cancel()
        listGameFavoriteJob = lifecycleScope.launch {
            favoriteViewModel.favoriteGames.collectLatest {
                favoriteAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(favoriteModule)
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return fragmentFavoriteBinding.root
    }

    private val itemTouchSlider = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val favoriteGames = favoriteAdapter.getSwipedData(swipedPosition)
                Toast.makeText(
                    context,
                    getString(R.string.removeFavoriteMessage, favoriteGames?.name),
                    Toast.LENGTH_LONG
                ).show()
                favoriteGames?.let { favoriteViewModel.deleteFavoriteGames(it.gamesId) }
            }
        }
    })

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteBinding = null
    }
}