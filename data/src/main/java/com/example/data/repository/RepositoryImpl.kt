package com.example.data.repository

import com.example.data.remote.error.RemoteFailureHandler
import com.example.data.remote.mapper.RepoMapper
import com.example.data.remote.retrofit.ApiService
import com.example.data.remote.retrofit.CryptoApi
import com.example.domain.core.error.Failure
import com.example.domain.core.functional.Either
import com.example.domain.core.functional.safeSuspend
import com.example.domain.model.CoinInfo
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RepositoryImpl constructor(
    private val api: CryptoApi,
    private val mapper: RepoMapper,
    private val remoteCoroutineErrorHandler: RemoteFailureHandler
): Repository {
    override suspend fun getCryptoTokenListing(currency: String): Either<Failure, List<CoinInfo>> {
        return safeSuspend ( remoteCoroutineErrorHandler ) {
            val response = api.getCoinListing(currency)
            val data = mapper.map(response.listing)
            Either.Right(data)
        }
    }

}