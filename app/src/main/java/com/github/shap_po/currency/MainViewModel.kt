package com.github.shap_po.currency

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class MainViewModel: ViewModel() {
    private val _date = MutableLiveData(Calendar.getInstance())
    val date: LiveData<Calendar>
        get() = _date

    fun setDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        _date.value = calendar
    }

    fun getDateString(): String {
        val calendar = _date.value ?: return "" // should not happen

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // months are 0-based
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        @SuppressLint("DefaultLocale")
        return String.format("%02d.%02d.%04d", day, month, year)
    }
}