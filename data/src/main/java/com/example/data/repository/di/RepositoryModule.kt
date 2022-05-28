package com.example.data.repository.di

import com.example.data.remote.error.RemoteFailureHandler
import com.example.data.remote.mapper.RepoMapper
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single <Repository> { RepositoryImpl(get(), RepoMapper(), RemoteFailureHandler()) }
}