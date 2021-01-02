package com.arudo.blown.core.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arudo.blown.R
import com.arudo.blown.core.utils.vo.Status
import com.arudo.blown.core.viewmodel.ViewModelFactory
import com.arudo.blown.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding.progressBar.visibility = View.VISIBLE
        fragmentHomeBinding.notificationError.visibility = View.GONE
        fragmentHomeBinding.rvHorizontalGame.visibility = View.GONE
        homeViewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity())).get(
                HomeViewModel::class.java
            )
        val homeAdapter = HomeAdapter()
        fragmentHomeBinding.rvHorizontalGame.adapter = homeAdapter
        homeViewModel.games.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        statusLayoutVisibility("Loading")
                    }
                    Status.SUCCESS -> {
                        if (it.data?.isNotEmpty() == true) {
                            statusLayoutVisibility("Success")
                            homeAdapter.setData(it.data)
                        } else {
                            statusLayoutVisibility("Error")
                        }
                    }
                    Status.ERROR -> {
                        statusLayoutVisibility("Error")
                    }
                }
            }
        })
    }

    private fun statusLayoutVisibility(status: String) {
        fragmentHomeBinding.progressBar.visibility = View.GONE
        fragmentHomeBinding.notificationError.visibility = View.GONE
        fragmentHomeBinding.rvHorizontalGame.visibility = View.GONE

        if (status == "Success") {
            fragmentHomeBinding.rvHorizontalGame.visibility = View.VISIBLE
        } else if (status == "Error") {
            fragmentHomeBinding.notificationError.visibility = View.VISIBLE
        } else if (status == "Loading") {
            fragmentHomeBinding.progressBar.visibility = View.VISIBLE
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}