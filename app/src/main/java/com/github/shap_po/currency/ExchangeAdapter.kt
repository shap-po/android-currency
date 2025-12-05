package com.github.shap_po.currency

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.shap_po.currency.ExchangeAdapter.ViewHolder
import com.github.shap_po.currency.databinding.ExchangeRateBinding
import com.github.shap_po.currency.retrofit.ExchangeRate


class ExchangeAdapter : ListAdapter<ExchangeRate, ViewHolder>(DiffCallback()) {
    class ViewHolder(val binding: ExchangeRateBinding) : RecyclerView.ViewHolder(binding.root)
    class DiffCallback : DiffUtil.ItemCallback<ExchangeRate>() {
        override fun areItemsTheSame(
            oldItem: ExchangeRate,
            newItem: ExchangeRate
        ): Boolean {
            return oldItem.currency == newItem.currency
        }

        override fun areContentsTheSame(
            oldItem: ExchangeRate,
            newItem: ExchangeRate
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExchangeRateBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(viewHolder.binding) {
            tvFrom.text = item.baseCurrency
            tvTo.text = item.currency
            tvBuy.text = String.format("%.4f", item.purchaseRateNB)
            tvSell.text = String.format("%.4f", item.saleRateNB)
        }
    }
}