package com.github.shap_po.currency.retrofit


import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("baseCurrency")
    val baseCurrency: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("purchaseRate")
    val purchaseRate: Double,
    @SerializedName("purchaseRateNB")
    val purchaseRateNB: Double,
    @SerializedName("saleRate")
    val saleRate: Double,
    @SerializedName("saleRateNB")
    val saleRateNB: Double
)