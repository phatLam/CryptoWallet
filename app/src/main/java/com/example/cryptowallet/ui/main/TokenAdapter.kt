package com.example.cryptowallet.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowallet.R
import com.example.cryptowallet.databinding.ItemTokenBinding
import com.example.domain.model.CoinInfo
import com.example.domain.model.PriceStatus

class TokenAdapter(): RecyclerView.Adapter<TokenViewHolder>() {
    private val tokenList: MutableList<CoinInfo> = mutableListOf()

    fun setList(newList: List<CoinInfo>) {
        val diffUtil = TokenDiffUtil(newList, tokenList)
        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        tokenList.clear()
        tokenList.addAll(newList)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TokenViewHolder {
        val binding = ItemTokenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TokenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TokenViewHolder, position: Int) {
        holder.bind(tokenList[position])
    }

    override fun onBindViewHolder(
        holder: TokenViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val item = payloads[0] as CoinInfo
            holder.bind(item)
        }
    }
    override fun getItemCount(): Int {
        return tokenList.size
    }


}

class TokenViewHolder(private val binding: ItemTokenBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CoinInfo) {
        binding.info = item
        setPriceTextColor(item.status)
        binding.executePendingBindings()
    }
    private fun setPriceTextColor(status: PriceStatus) {
        val color = when (status) {
            PriceStatus.DOWN -> R.color.red
            PriceStatus.EQUAL -> R.color.black
            else -> R.color.green
        }
        binding.tvBuyPrice.setTextColor(ContextCompat.getColor(binding.root.context, color))
        binding.tvSellPrice.setTextColor(ContextCompat.getColor(binding.root.context, color))
    }
}