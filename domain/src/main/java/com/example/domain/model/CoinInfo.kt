package com.example.domain.model

import java.math.BigDecimal


data class CoinInfo (
	val base : String,
	val counter : String,
	val buyPrice : BigDecimal,
	val sellPrice : BigDecimal,
	val icon : String,
	val name : String,
	val currencyConversion: String,
	var status : PriceStatus = PriceStatus.EQUAL
)

enum class PriceStatus {
	UP,
	DOWN,
	EQUAL,
}