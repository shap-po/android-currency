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
            viewModel.fetchData(year, month, dayOfMonth)
        }
        viewModel.status.observe(this) {
            when (it) {
                Status.LOADING -> {
                    binding.textView.text = "Loading..."
                }

                Status.ERROR -> {
                    binding.textView.text = "Error..."
                }

                is Status.SUCCESS -> {
                    binding.textView.append(it.result.toString())
                }
            }
        }
    }

    companion object {
        const val TAG = "XCURR"
    }
}