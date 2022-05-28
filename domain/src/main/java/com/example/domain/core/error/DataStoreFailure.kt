package com.example.domain.core.error

sealed class DataStoreFailure : Failure {

    object IOFailure : DataStoreFailure()

    object UnknownFailure : DataStoreFailure()
}