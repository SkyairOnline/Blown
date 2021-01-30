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
import androidx.fragment.app.Fragment
import com.arudo.blown.R
import com.arudo.blown.core.main.search.SearchAdapter
import com.arudo.blown.core.source.local.Resource
import com.arudo.blown.core.utils.Status
import com.arudo.blown.databinding.FragmentSearchBinding
import com.arudo.blown.ui.detail.DetailActivity
import com.arudo.blown.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter = SearchAdapter()
        fragmentSearchBinding.notificationErrorSearch.visibility = View.GONE
        fragmentSearchBinding.notificationForSearch.visibility = View.VISIBLE
        fragmentSearchBinding.progressBarSearch.visibility = View.GONE
        fragmentSearchBinding.rvHorizontalSearchGame.visibility = View.GONE
        fragmentSearchBinding.rvHorizontalSearchGame.adapter = searchAdapter
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
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                loadSearch(newText)
                return true
            }
        })
    }

    private fun loadSearch(text: String) {
        searchViewModel.setGameDetailId(text)
        searchViewModel.searchGames.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        statusLayoutVisibility(Status.Loading)
                    }
                    is Resource.Success -> {
                        searchAdapter.setData(it.data!!)
                        statusLayoutVisibility(Status.Success)
                    }
                    is Resource.Error -> {
                        statusLayoutVisibility(Status.Error)
                    }
                }
            }
        })
        searchAdapter.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it)
            startActivity(intent)
        }
    }

    private fun statusLayoutVisibility(status: Status) {
        fragmentSearchBinding.notificationForSearch.visibility = View.GONE
        fragmentSearchBinding.notificationErrorSearch.visibility = View.GONE
        fragmentSearchBinding.progressBarSearch.visibility = View.GONE
        fragmentSearchBinding.rvHorizontalSearchGame.visibility = View.GONE

        when (status) {
            Status.Success -> {
                fragmentSearchBinding.rvHorizontalSearchGame.visibility = View.VISIBLE
            }
            Status.Error -> {
                fragmentSearchBinding.notificationErrorSearch.visibility = View.VISIBLE
            }
            Status.Loading -> {
                fragmentSearchBinding.progressBarSearch.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return fragmentSearchBinding.root
    }
}