package com.example.cryptowallet.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cryptowallet.R
import com.example.cryptowallet.databinding.MainFragmentBinding
import com.example.domain.core.error.ApiFailure
import com.example.domain.core.error.Failure
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    private val adapter: TokenAdapter by lazy { TokenAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = adapter
        mainViewModel.tokenListingLiveData.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        mainViewModel.failureLiveData.observe(viewLifecycleOwner) {
            showNetworkError(it)
        }
        binding.imgSearch.setOnClickListener {
            openSearchScreen()
        }
    }
    private fun openSearchScreen() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun showNetworkError(failure: Failure) {
        val message = when (failure) {
            is ApiFailure.Connection -> {
                getString(R.string.msg_no_internet_error)
            }
            is ApiFailure.Server -> {
                val errorMessage = failure.errorMessage
                errorMessage.ifEmpty {
                    getString(R.string.msg_unexpected_error)
                }
            }
            is ApiFailure.Unknown -> {
                getString(R.string.msg_unexpected_error)
            }
            else -> ""
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}