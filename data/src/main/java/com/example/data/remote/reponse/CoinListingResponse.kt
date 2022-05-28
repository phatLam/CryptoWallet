package com.example.data.remote.reponse

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CoinListingResponse(
	@SerializedName("data")
	val listing: List<CoinInfoResponse>
)

data class CoinInfoResponse (

	@SerializedName("base") val base : String,
	@SerializedName("counter") val counter : String,
	@SerializedName("buy_price") val buy_price : BigDecimal,
	@SerializedName("sell_price") val sell_price : BigDecimal,
	@SerializedName("icon") val icon : String,
	@SerializedName("name") val name : String
)

