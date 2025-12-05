package com.github.shap_po.currency.retrofit


import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponce(
    @SerializedName("bank")
    val bank: String,
    @SerializedName("baseCurrency")
    val baseCurrency: Int,
    @SerializedName("baseCurrencyLit")
    val baseCurrencyLit: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("exchangeRate")
    val exchangeRates: List<ExchangeRate>
)