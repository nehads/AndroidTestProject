package com.example.androidtestproject.data.repository

import com.example.androidtestproject.data.remote.RemoteDataSource
import com.example.androidtestproject.data.repository.preference.PreferenceHelper
import com.example.androidtestproject.model.DataModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Neha Dessai on 30-03-2023.
 */

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mPrefHelper: PreferenceHelper
) {
    suspend fun getData(currentPage: Int): DataModel {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getData(currentPage)
        }
    }

    suspend fun getCount(): Flow<Int> {
        return flow<Int> {
            emit(mPrefHelper.getCount())
        }.flowOn(Dispatchers.IO)
    }

    fun saveCount(count: Int) {
        mPrefHelper.saveCount(count)
    }

}