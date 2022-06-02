package com.example.cryptowallet.di

import android.content.Context
import com.example.cryptowallet.ui.main.MainViewModel
import com.example.cryptowallet.util.SharedPreference
import com.example.data.remote.error.RemoteFailureHandler
import com.example.data.remote.mapper.RepoMapper
import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.domain.usecase.CryptoTokenGetAllUserCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single <Repository> { RepositoryImpl(get(), RepoMapper(), RemoteFailureHandler()) }
    fun provideSharedPreference(context: Context) = SharedPreference(context)
    single { provideSharedPreference(androidContext()) }
    viewModel {
        MainViewModel(CryptoTokenGetAllUserCase(get()))
    }

}

