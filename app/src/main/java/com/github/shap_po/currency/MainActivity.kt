package com.github.shap_po.currency

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.shap_po.currency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendarView.maxDate = System.currentTimeMillis(); // cant know the future
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)
        }
        viewModel.data.observe(this) {
            binding.textView.text = viewModel.data.value?.getDateString()
            binding.textView.append(viewModel.data.value?.exchangeRates.toString())
        }
    }

    companion object {
        const val TAG = "XCURR"
    }
}