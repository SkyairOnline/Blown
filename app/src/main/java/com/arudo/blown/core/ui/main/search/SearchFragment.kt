package com.arudo.blown.core.ui.main.search

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.arudo.blown.R
import com.arudo.blown.core.data.source.local.Resource
import com.arudo.blown.core.utils.Status
import com.arudo.blown.databinding.FragmentSearchBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private val searchAdapter = SearchAdapter()
    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchBinding = FragmentSearchBinding.bind(view)
        fragmentSearchBinding.notificationErrorSearch.visibility = View.GONE
        fragmentSearchBinding.notificationForSearch.visibility = View.VISIBLE
        fragmentSearchBinding.progressBarSearch.visibility = View.GONE
        fragmentSearchBinding.searchBarGame.visibility = View.VISIBLE
        fragmentSearchBinding.rvHorizontalSearchGame.visibility = View.GONE
        fragmentSearchBinding.rvHorizontalSearchGame.adapter = searchAdapter
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = fragmentSearchBinding.searchBarGame.apply {
            isIconifiedByDefault = false
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
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
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}