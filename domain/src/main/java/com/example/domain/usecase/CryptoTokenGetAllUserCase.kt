package com.example.domain.usecase

import com.example.domain.CoroutineUseCase
import com.example.domain.core.error.Failure
import com.example.domain.core.functional.Either
import com.example.domain.model.CoinInfo
import com.example.domain.repository.Repository

class CryptoTokenGetAllUserCase constructor(private val repository: Repository): CoroutineUseCase<String, Either<Failure, List<CoinInfo>>>(){
    override suspend fun execute(params: String): Either<Failure, List<CoinInfo>> {
        return repository.getCryptoTokenListing(params)
    }


}