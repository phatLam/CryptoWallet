package com.example.data.remote.di

import com.example.data.remote.retrofit.ApiService
import com.example.data.remote.retrofit.CryptoApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    fun provideCryptoApi(): CryptoApi {
        return CryptoApi("https://www.coinhako.com/")
    }

    single { provideCryptoApi() }
}