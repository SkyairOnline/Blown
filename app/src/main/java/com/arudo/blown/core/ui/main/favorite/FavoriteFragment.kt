package com.arudo.blown.core.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.arudo.blown.R
import com.arudo.blown.core.utils.Status
import com.arudo.blown.databinding.FragmentFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFavoriteBinding = FragmentFavoriteBinding.bind(view)
        statusLayoutVisibility(Status.Loading)
        favoriteAdapter = FavoriteAdapter()
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.adapter = favoriteAdapter
        itemTouchSlider.attachToRecyclerView(fragmentFavoriteBinding.rvHorizontalFavoriteGame)
        favoriteViewModel.favoriteGames.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.size > 0) {
                    favoriteAdapter.submitList(it)
                    favoriteAdapter.notifyDataSetChanged()
                    statusLayoutVisibility(Status.Success)
                } else {
                    statusLayoutVisibility(Status.Error)
                }
            }
        })
    }

    private fun statusLayoutVisibility(status: Status) {
        fragmentFavoriteBinding.progressBarFavorite.visibility = View.GONE
        fragmentFavoriteBinding.notificationForFavorite.visibility = View.GONE
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.visibility = View.GONE

        when (status) {
            Status.Success -> {
                fragmentFavoriteBinding.rvHorizontalFavoriteGame.visibility = View.VISIBLE
            }
            Status.Error -> {
                fragmentFavoriteBinding.notificationForFavorite.visibility = View.VISIBLE
            }
            Status.Loading -> {
                fragmentFavoriteBinding.progressBarFavorite.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
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
                val swipedPosition = viewHolder.adapterPosition
                val favoriteGames = favoriteAdapter.getSwipedData(swipedPosition)
                Toast.makeText(
                    context,
                    getString(R.string.removeFavoriteMessage, favoriteGames?.name),
                    Toast.LENGTH_LONG
                ).show()
                favoriteGames?.let { favoriteViewModel.deleteFavoriteGames(it.id) }
            }
        }
    })
}