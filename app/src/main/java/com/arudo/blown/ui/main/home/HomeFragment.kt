package com.arudo.blown.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arudo.blown.R
import com.arudo.blown.core.main.home.HomeAdapter
import com.arudo.blown.databinding.FragmentHomeBinding
import com.arudo.blown.ui.detail.DetailActivity
import com.arudo.blown.ui.main.GamesLoadStateAdapter
import com.arudo.blown.ui.setting.SettingActivity
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
        setHasOptionsMenu(true)
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingMenu -> startActivity(Intent(context, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeJob?.cancel()
        homeAdapter = null
        _fragmentHomeBinding = null
    }
}