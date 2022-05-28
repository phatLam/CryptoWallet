package com.example.cryptowallet.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.CoinInfo
import com.example.domain.model.PriceStatus

class TokenDiffUtil constructor(
    private val newList: List<CoinInfo>,
    private val oldList: List<CoinInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.base == newItem.base
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val status =
            when (newList[newItemPosition].buyPrice.compareTo(oldList[oldItemPosition].buyPrice)) {
                1 -> PriceStatus.UP
                0 -> PriceStatus.EQUAL
                else -> PriceStatus.DOWN
            }
        val item = newList[newItemPosition]
        item.status = status
        return item
    }

}