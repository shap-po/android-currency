package com.github.shap_po.currency

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.shap_po.currency.MainActivity.Companion.TAG
import com.github.shap_po.currency.retrofit.ExchangeRatesResponce
import com.github.shap_po.currency.retrofit.PrivatBankRetrofit
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

sealed class Status {
    data class SUCCESS(val result: ExchangeRatesResponce) : Status()
    object ERROR : Status()
    object LOADING : Status()
}

class MainViewModel : ViewModel() {
    private val privatBankService = PrivatBankRetrofit.getService()
    private val _status = MutableLiveData<Status>(Status.LOADING)
    val status: LiveData<Status>
        get() = _status
    private val _selectedDate = MutableLiveData(System.currentTimeMillis())
    val selectedDate: LiveData<Long>
        get() = _selectedDate

    private var job: Job? = null

    init {
        val date = java.util.Calendar.getInstance()
        var year = date.get(java.util.Calendar.YEAR)
        var month = date.get(java.util.Calendar.MONTH) + 1
        var day = date.get(java.util.Calendar.DAY_OF_MONTH)

        fetchData(year, month, day)
    }

    fun fetchData(year: Int, month: Int, day: Int) {
        job?.cancel() // cancel previous job if any

        // save selected date
        _selectedDate.value = java.util.Calendar.getInstance().apply {
            set(year, month - 1, day)
        }.timeInMillis

        _status.value = Status.LOADING

        val dateString = getDateString(year, month, day)
        job = viewModelScope.launch {
            val result = privatBankService.getExchangeRates(dateString)
            if (result == null) {
                Log.e(TAG, "Failed to fetch exchange rates for $dateString")
                return@launch
            }
            Log.d(TAG, "Fetched exchange rates for $dateString: $result")

            _status.value = Status.SUCCESS(result)
        }
    }

    @SuppressLint("DefaultLocale")
    fun getDateString(year: Int, month: Int, day: Int): String {
        return String.format("%02d.%02d.%04d", day, month, year)
    }
}