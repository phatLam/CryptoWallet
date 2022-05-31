package com.example.cryptowallet.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.domain.core.error.ApiFailure
import com.example.domain.core.error.Failure
import com.example.domain.model.CoinInfo
import com.example.domain.usecase.CryptoTokenGetAllUserCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainViewModel(private val useCase: CryptoTokenGetAllUserCase) : ViewModel() {
    private val _failure = MutableLiveData<Failure>()
    val failureLiveData: LiveData<Failure> = _failure
    private val _tokenListing = MutableLiveData<List<CoinInfo>>()
    val tokenListingLiveData: LiveData<List<CoinInfo>> = _tokenListing
    val queryFlow = MutableStateFlow("")
    private val _searchFlow = queryFlow.debounce(300)
        .distinctUntilChanged()
        .mapLatest { query ->
        tokenListingLiveData.value?.filter { info ->
            info.base.lowercase().contains(query.lowercase()) && query.isNotEmpty()
        }
    }.flowOn(Dispatchers.Default)

    private val _searchedTokenListingState = MutableStateFlow<List<CoinInfo>>(emptyList())
    val searchedTokenListingState: StateFlow<List<CoinInfo>> = _searchedTokenListingState
    init {
        fetchTokenListingByPeriod()
        searchAction()
    }

    private fun getCryptoTokenListing() {
        viewModelScope.launch {
            useCase.invoke("USD").fold(fnL = {
                _failure.value = it
                handleError(it)
            }, {
                _tokenListing.value = it
            })
        }

    }
    private fun searchAction() {
        viewModelScope.launch {
            _searchFlow.collect {
                _searchedTokenListingState.value = it?: emptyList()
            }
        }
    }
    private var fetchTokenListingJob: Job?= null
    fun fetchTokenListingByPeriod() {
        fetchTokenListingJob = viewModelScope.launch {
            tickerFlow(5.seconds)
                .collect {
                    getCryptoTokenListing()
                }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun tickerFlow(period: Duration) = flow {
        while (true) {
            emit(Unit)
            delay(period)
        }
    }
    fun removeSearchedData(){
        queryFlow.value = ""
        _searchedTokenListingState.value = emptyList()
    }

    private fun handleError(failure: Failure) {
        if (failure is ApiFailure.Connection){
            Log.i("Phat", "Network")
            fetchTokenListingJob?.cancel()
        }
    }
}