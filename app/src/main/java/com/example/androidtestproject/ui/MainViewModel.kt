package com.example.androidtestproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtestproject.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Neha Dessai on 01-04-2023.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _countResponse: MutableLiveData<Int> =
        MutableLiveData()
    val countResponse: LiveData<Int> =
        _countResponse

    //get count from local preference
    fun getCount() = viewModelScope.launch {
        repository.getCount().onStart {
        }.collect { values ->
            _countResponse.value = values
        }
    }

    //once app opens, update the value to existing count + 1
    fun updateCount() = viewModelScope.launch {
        repository.getCount().collect { count ->
            repository.saveCount(count + 1)
        }
    }
}