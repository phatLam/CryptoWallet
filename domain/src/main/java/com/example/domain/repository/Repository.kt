package com.example.domain.repository

import com.example.domain.core.error.Failure
import com.example.domain.core.functional.Either
import com.example.domain.model.CoinInfo

interface Repository {
    suspend fun getCryptoTokenListing(currency: String): Either<Failure, List<CoinInfo>>

    fun abc(){}
}