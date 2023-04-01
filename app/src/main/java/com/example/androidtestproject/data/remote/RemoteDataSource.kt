package com.example.androidtestproject.data.remote

import javax.inject.Inject

/**
 * Created by Neha Dessai on 30-03-2023.
 */

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getData(page: Int) = apiService.getData(page)

}