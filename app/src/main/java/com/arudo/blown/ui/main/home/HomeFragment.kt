package com.arudo.blown.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arudo.blown.core.main.home.HomeAdapter
import com.arudo.blown.core.source.local.Resource
import com.arudo.blown.core.utils.Status
import com.arudo.blown.databinding.FragmentHomeBinding
import com.arudo.blown.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding.progressBar.visibility = View.VISIBLE
        fragmentHomeBinding.notificationError.visibility = View.GONE
        fragmentHomeBinding.rvHorizontalGame.visibility = View.GONE
        val homeAdapter = HomeAdapter()
        fragmentHomeBinding.rvHorizontalGame.adapter = homeAdapter
        homeViewModel.games.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        statusLayoutVisibility(Status.Loading)
                    }
                    is Resource.Success -> {
                        homeAdapter.setData(it.data!!)
                        statusLayoutVisibility(Status.Success)
                    }
                    is Resource.Error -> {
                        statusLayoutVisibility(Status.Error)
                    }
                }
            }
        })
        homeAdapter.onClickListenerItem = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it)
            startActivity(intent)
        }
    }

    private fun statusLayoutVisibility(status: Status) {
        fragmentHomeBinding.progressBar.visibility = View.GONE
        fragmentHomeBinding.notificationError.visibility = View.GONE
        fragmentHomeBinding.rvHorizontalGame.visibility = View.GONE

        when (status) {
            Status.Success -> {
                fragmentHomeBinding.rvHorizontalGame.visibility = View.VISIBLE
            }
            Status.Error -> {
                fragmentHomeBinding.notificationError.visibility = View.VISIBLE
            }
            Status.Loading -> {
                fragmentHomeBinding.progressBar.visibility = View.VISIBLE
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }
}