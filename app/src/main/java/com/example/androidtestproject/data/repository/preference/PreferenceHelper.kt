package com.example.androidtestproject.data.repository.preference

/**
 * Created by Neha Dessai on 30-03-2023.
 */

interface PreferenceHelper {
    fun getCount(): Int

    fun saveCount(count: Int)
}