package com.github.shap_po.currency

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.shap_po.currency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()
    private val adapter = ExchangeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendarView.maxDate = System.currentTimeMillis(); // cant know the future
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.fetchData(year, month, dayOfMonth)
        }
        binding.rvExchange.adapter = adapter
        viewModel.status.observe(this) { status ->
            binding.progressBar.visibility =
                if (status == Status.LOADING) View.VISIBLE else View.GONE
            when (status) {
                Status.LOADING -> {
                    adapter.submitList(emptyList())
                }

                Status.ERROR -> {
                    Toast.makeText(this, "Error fetching data!", Toast.LENGTH_SHORT).show()
                }

                is Status.SUCCESS -> {
                    Log.d(TAG, "Got exchange rates: ${status.result.exchangeRates}")
                    adapter.submitList(status.result.exchangeRates)
                }
            }
        }
    }


    companion object {
        const val TAG = "XCURR"
    }
}