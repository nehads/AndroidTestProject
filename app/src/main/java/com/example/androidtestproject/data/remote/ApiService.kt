package com.example.androidtestproject.data.remote

import com.example.androidtestproject.model.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Neha Dessai on 30-03-2023.
 */
interface ApiService {

    companion object {
        private const val API = "/api"
        const val USERS = "$API/users"
    }

    @GET(USERS)
    suspend fun getData(@Query("page") page: Int): DataModel

}