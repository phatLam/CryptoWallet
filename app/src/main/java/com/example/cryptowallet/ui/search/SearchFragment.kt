package com.example.cryptowallet.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.cryptowallet.databinding.FragmentSearchBinding
import com.example.cryptowallet.ui.main.MainViewModel
import com.example.cryptowallet.ui.main.TokenAdapter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val adapter: TokenAdapter by lazy { TokenAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adapter = adapter
        binding.tvCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            mainViewModel.queryFlow.value = text.toString()
        }
        lifecycleScope.launchWhenStarted {
            mainViewModel.searchedTokenListingState.collect {
                adapter.setList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.removeSearchedData()
    }


}