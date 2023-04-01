package com.example.androidtestproject.data.repository.preference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Neha Dessai on 30-03-2023.
 */

@Singleton
class SharedPrefManager @Inject constructor(private val mSharedPreferences: SharedPreferences) :
    PreferenceHelper {

    companion object {
        const val APP_OPEN_COUNT = "app_open_count"
    }

    private inline fun SharedPreferences.edit(operation: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.operation()
        editor.apply()
    }

    fun SharedPreferences.Editor.set(pair: Pair<String, Any?>) {
        when (pair.second) {
            is String? -> putString(pair.first, pair.second as String?)
            is Int -> putInt(pair.first, pair.second as Int)
            is Boolean -> putBoolean(pair.first, pair.second as Boolean)
            is Float -> putFloat(pair.first, pair.second as Float)
            is Long -> putLong(pair.first, pair.second as Long)
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    override fun getCount(): Int {
        return mSharedPreferences.get(APP_OPEN_COUNT, 0) ?: 0
    }

    override fun saveCount(count: Int) {
        mSharedPreferences.edit {
            set(Pair(APP_OPEN_COUNT, count))
        }
    }

}