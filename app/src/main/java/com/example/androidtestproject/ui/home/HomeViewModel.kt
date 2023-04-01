package com.example.androidtestproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidtestproject.data.repository.AppRepository
import com.example.androidtestproject.model.UserModel
import com.example.androidtestproject.ui.paging.UsersPagingSource
import com.example.androidtestproject.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Neha Dessai on 30-03-2023.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    val getData: Flow<PagingData<UserModel>> =
        Pager(
            config = PagingConfig(
                Constants.PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            UsersPagingSource(repository)
        }.flow.cachedIn(viewModelScope)
}