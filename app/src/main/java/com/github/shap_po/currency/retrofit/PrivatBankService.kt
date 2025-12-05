package com.github.shap_po.currency.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface PrivatBankService {
    @GET("exchange_rates")
    suspend fun getExchangeRates(@Query("date") date: String?): ExchangeRatesResponce?
}