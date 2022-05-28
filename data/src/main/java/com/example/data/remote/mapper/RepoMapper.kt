package com.example.data.remote.mapper

import com.example.data.remote.reponse.CoinInfoResponse
import com.example.domain.model.CoinInfo

class RepoMapper  {
    fun map(items: List<CoinInfoResponse>, currency: String): List<CoinInfo> {
        return items.map {
            CoinInfo(
                base = it.base,
                counter = it.counter,
                name = it.name,
                buyPrice = it.buy_price,
                sellPrice = it.sell_price,
                icon = it.icon,
                currencyConversion = currency
            )
        }

    }

}