package com.example.domain.core.usecase


abstract class BasicUseCase<in Params , out Result>  {

    protected abstract suspend fun execute(params: Params): Result

    suspend operator fun invoke(params: Params): Result {
        return execute(params)
    }
}