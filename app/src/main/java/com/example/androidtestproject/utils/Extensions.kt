package com.example.androidtestproject.utils

import android.app.ActionBar
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.androidtestproject.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Neha Dessai on 30-03-2023.
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.showToast(e: Throwable) {
    val msg = when (e) {
        is HttpException -> {
            try {
                Gson().fromJson(
                    e.response()?.errorBody()?.charStream(),
                    ErrorResponse::class.java
                ).error

            } catch (e: IOException) {
                Constants.ERROR_SOMETHING_WENT_WRONG
            }
        }
        is SocketTimeoutException ->
            Constants.ERROR_COULD_NOT_CONNECT_TO_SERVER

        is UnknownHostException ->
            Constants.ERROR_NO_INTERNET

        else -> Constants.ERROR_SOMETHING_WENT_WRONG
    }

    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}