package com.example.data.remote.retrofit

import com.example.data.remote.reponse.CoinListingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v3/price/all_prices_for_mobile")
    suspend fun getCoinListing(@Query("counter_currency") currency: String): CoinListingResponse
}