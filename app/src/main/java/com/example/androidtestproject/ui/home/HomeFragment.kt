package com.example.androidtestproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtestproject.databinding.FragmentHomeBinding
import com.example.androidtestproject.ui.adapters.LoadMoreAdapter
import com.example.androidtestproject.ui.adapters.UsersAdapter
import com.example.androidtestproject.utils.hide
import com.example.androidtestproject.utils.show
import com.example.androidtestproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()

        userAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh !is LoadState.Loading ||
                loadState.append !is LoadState.Loading
            ) {
                binding.progressBar.hide()
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    requireContext().showToast(it.error)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.getData.collectLatest { response ->
                binding.apply {
                    binding.progressBar.hide()
                    binding.rvUsers.show()
                }
                userAdapter.submitData(response)
            }
        }

        lifecycleScope.launchWhenCreated {
            userAdapter.loadStateFlow.collect {
                val state = it.refresh
                binding.progressBar.isVisible = state is LoadState.Loading
            }
        }

        userAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun initRecyclerview() {
        binding.apply {
            binding.rvUsers.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = userAdapter.withLoadStateFooter(
                    LoadMoreAdapter {
                        userAdapter.retry()
                    }
                )
            }
        }
    }
}