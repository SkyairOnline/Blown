package com.arudo.blown.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arudo.blown.core.main.home.HomeAdapter
import com.arudo.blown.databinding.FragmentHomeBinding
import com.arudo.blown.ui.detail.DetailActivity
import com.arudo.blown.ui.main.GamesLoadStateAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding!!
    private var homeAdapter: HomeAdapter? = null
    private var homeJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter()

        fragmentHomeBinding.rvHorizontalGame.adapter = homeAdapter?.withLoadStateHeaderAndFooter(
            header = GamesLoadStateAdapter { homeAdapter?.retry() },
            footer = GamesLoadStateAdapter { homeAdapter?.retry() }
        )

        homeAdapter?.addLoadStateListener {
            fragmentHomeBinding.rvHorizontalGame.isVisible =
                it.source.refresh is LoadState.NotLoading
            fragmentHomeBinding.notificationLoading.isVisible =
                it.source.refresh is LoadState.Loading
            fragmentHomeBinding.notificationError.root.isVisible =
                it.source.refresh is LoadState.Error
        }
        getListGame()
        homeAdapter?.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("extra_detail", it)
            startActivity(intent)
        }
    }

    private fun getListGame() {
        homeJob?.cancel()
        homeJob = lifecycleScope.launch {
            homeViewModel.games.collectLatest {
                homeAdapter?.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentHomeBinding = null
        homeJob?.cancel()
        homeAdapter = null
    }
}