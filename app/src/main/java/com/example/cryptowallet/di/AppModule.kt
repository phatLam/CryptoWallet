package com.example.cryptowallet.di

import com.example.cryptowallet.ui.main.MainViewModel
import com.example.data.remote.error.RemoteFailureHandler
import com.example.data.remote.mapper.RepoMapper
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.domain.usecase.CryptoTokenGetAllUserCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single <Repository> { RepositoryImpl(get(), RepoMapper(), RemoteFailureHandler()) }

    viewModel {
        MainViewModel(CryptoTokenGetAllUserCase(get()))
    }

}

