package com.arudo.blown.ui.main.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arudo.blown.R
import com.arudo.blown.core.main.search.SearchAdapter
import com.arudo.blown.databinding.FragmentSearchBinding
import com.arudo.blown.ui.detail.DetailActivity
import com.arudo.blown.ui.main.GamesLoadStateAdapter
import com.arudo.blown.ui.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private var searchAdapter: SearchAdapter? = null
    private var _fragmentSearchBinding: FragmentSearchBinding? = null
    private val fragmentSearchBinding get() = _fragmentSearchBinding!!
    private var searchJob: Job? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter = SearchAdapter()
        fragmentSearchBinding.notificationForSearch.root.visibility = View.VISIBLE
        fragmentSearchBinding.rvHorizontalSearchGame.adapter =
            searchAdapter?.withLoadStateHeaderAndFooter(
                header = GamesLoadStateAdapter { searchAdapter?.retry() },
                footer = GamesLoadStateAdapter { searchAdapter?.retry() }
            )
        searchAdapter?.addLoadStateListener {
            fragmentSearchBinding.rvHorizontalSearchGame.isVisible =
                it.source.refresh is LoadState.NotLoading
            fragmentSearchBinding.notificationLoadingSearch.isVisible =
                it.source.refresh is LoadState.Loading
            fragmentSearchBinding.notificationErrorSearch.root.isVisible =
                it.source.refresh is LoadState.Error
        }
        searchAdapter?.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("extra_detail", it)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_bar_manu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.app_bar_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
            actionView = searchView
        }
        val searchIcon: ImageView = searchView.findViewById(
            searchView.context.resources.getIdentifier(
                "android:id/search_mag_icon",
                null,
                null
            )
        )
        val searchCloseIcon: ImageView = searchView.findViewById(
            searchView.context.resources.getIdentifier(
                "android:id/search_close_btn",
                null,
                null
            )
        )
        searchView.isIconifiedByDefault = false
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = getString(R.string.searchHint)
        searchIcon.setImageResource(R.drawable.ic_search)
        searchCloseIcon.setImageResource(R.drawable.ic_clear)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loadSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun loadSearch(text: String) {
        if (text.isNotEmpty()) {
            fragmentSearchBinding.rvHorizontalSearchGame.scrollToPosition(0)
            fragmentSearchBinding.notificationForSearch.root.visibility = View.GONE
            getListSearchGame(text)
        }
    }


    private fun getListSearchGame(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            searchViewModel.searchGames(query).collectLatest {
                searchAdapter?.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return fragmentSearchBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentSearchBinding = null
        searchJob?.cancel()
        searchAdapter = null
    }
}