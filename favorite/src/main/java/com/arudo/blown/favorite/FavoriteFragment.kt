package com.arudo.blown.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arudo.blown.core.main.favorite.FavoriteAdapter
import com.arudo.blown.core.utils.Status
import com.arudo.blown.favorite.databinding.FragmentFavoriteBinding
import com.arudo.blown.favorite.di.favoriteModule
import com.arudo.blown.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var favoriteAdapter = FavoriteAdapter()
    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val fragmentFavoriteBinding get() = _fragmentFavoriteBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLayoutVisibility(Status.Loading)
        favoriteAdapter = FavoriteAdapter()
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.adapter = favoriteAdapter
        favoriteViewModel.favoriteGames.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                favoriteAdapter.setData(it)
                statusLayoutVisibility(Status.Success)
            } else {
                statusLayoutVisibility(Status.Error)
            }
        })
        favoriteAdapter.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("extra_detail", it)
            startActivity(intent)
        }
    }

    private fun statusLayoutVisibility(status: Status) {
        fragmentFavoriteBinding.progressBarFavorite.visibility = View.GONE
        fragmentFavoriteBinding.notificationFavoriteStart.root.visibility = View.GONE
        fragmentFavoriteBinding.rvHorizontalFavoriteGame.visibility = View.GONE

        when (status) {
            Status.Success -> {
                fragmentFavoriteBinding.rvHorizontalFavoriteGame.visibility = View.VISIBLE
            }
            Status.Error -> {
                fragmentFavoriteBinding.notificationFavoriteStart.root.visibility = View.VISIBLE
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
    ): View {
        loadKoinModules(favoriteModule)
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteBinding = null
    }
}