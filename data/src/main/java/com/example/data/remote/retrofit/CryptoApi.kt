package com.example.data.remote.retrofit

import com.example.data.remote.reponse.CoinListingResponse
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoApi(url: String): ApiService {
    private val service: ApiService
   init {
        val factory = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        val okHttpClientBuilder = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(okHttpClientBuilder)
            .build()
       service = retrofit.create(ApiService::class.java)
    }
    override suspend fun getCoinListing(currency: String): CoinListingResponse {
        return service.getCoinListing(currency)
    }
}