package com.example.androidtestproject.ui

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.ActivityMainBinding
import com.example.androidtestproject.databinding.FragmentHomeBinding
import com.example.androidtestproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.updateCount()
        mainViewModel.getCount()

        observeData()
    }

    private fun observeData() {
        mainViewModel.countResponse.observe(this) { response ->
            setActionbarTitle(response)
        }
    }

    private fun setActionbarTitle(count: Int) {
        supportActionBar?.title =
            resources.getQuantityString(R.plurals.app_opened_count, count, count)
    }
}


