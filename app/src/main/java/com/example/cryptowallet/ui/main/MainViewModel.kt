package com.example.cryptowallet.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.core.error.Failure
import com.example.domain.model.CoinInfo
import com.example.domain.usecase.CryptoTokenGetAllUserCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainViewModel(private val useCase: CryptoTokenGetAllUserCase) : ViewModel() {
    private val _failure = MutableLiveData<Failure>()
    val failureLiveData : LiveData<Failure> = _failure
    private val _tokenListing = MutableLiveData<List<CoinInfo>>()
    val tokenListingLiveData : LiveData<List<CoinInfo>> = _tokenListing
    init {
        fetchTokenListingByPeriod()
    }
    private fun getCryptoTokenListing() {
        viewModelScope.launch {
            useCase.invoke("USD").fold(fnL = {
                _failure.value = it
            }, {
                Log.i("Phat", it.size.toString())
                _tokenListing.value = it
            })
        }

    }

    private fun fetchTokenListingByPeriod() {
        viewModelScope.launch {
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
}