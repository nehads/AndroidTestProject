package com.example.androidtestproject.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidtestproject.data.repository.AppRepository
import com.example.androidtestproject.model.UserModel
import com.example.androidtestproject.utils.Constants

/**
 * Created by Neha Dessai on 30-03-2023.
 */

class UsersPagingSource(
    private val repository: AppRepository,
) : PagingSource<Int, UserModel>() {

    private lateinit var response: List<UserModel>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try {
            val page = params.key ?: Constants.DEFAULT_PAGE_SIZE

            repository.getData(page).let {
                response = it.userModel
            }

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }
}

