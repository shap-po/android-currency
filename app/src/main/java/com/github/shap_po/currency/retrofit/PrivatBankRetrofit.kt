package com.github.shap_po.currency.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PrivatBankRetrofit {
    private const val BASE_URL = "https://api.privatbank.ua/p24api/"

    fun getService(): PrivatBankService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PrivatBankService::class.java)
    }
}