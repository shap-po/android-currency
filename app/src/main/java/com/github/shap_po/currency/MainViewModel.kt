package com.github.shap_po.currency

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.shap_po.currency.MainActivity.Companion.TAG
import com.github.shap_po.currency.retrofit.ExchangeRate
import com.github.shap_po.currency.retrofit.PrivatBankRetrofit
import kotlinx.coroutines.launch
import java.util.Calendar

data class Data(val date: Calendar, val exchangeRates: List<ExchangeRate>) {
    constructor() : this(Calendar.getInstance(), emptyList())

    fun getDateString(): String {
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH) + 1 // months are 0-based
        val day = date.get(Calendar.DAY_OF_MONTH)

        @SuppressLint("DefaultLocale")
        return String.format("%02d.%02d.%04d", day, month, year)
    }
}

class MainViewModel : ViewModel() {
    private val privatBankService = PrivatBankRetrofit.getService()
    private val _data = MutableLiveData(Data())
    val data: LiveData<Data>
        get() = _data

    fun setDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        _data.value = Data(calendar, listOf())
        fetchCurrencyData()
    }

    fun fetchCurrencyData() {
        val currentData = _data.value ?: return
        val dateString = currentData.getDateString()
        viewModelScope.launch {
            val result = privatBankService.getExchangeRates(dateString)
            if (result == null) {
                Log.e(TAG, "Failed to fetch exchange rates for $dateString")
                return@launch
            }
            Log.d(TAG, "Fetched exchange rates for $dateString: $result")

            _data.value = Data(currentData.date, result.exchangeRates)
        }
    }
}