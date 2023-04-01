package com.example.androidtestproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.androidtestproject.BuildConfig
import com.example.androidtestproject.data.repository.preference.PreferenceHelper
import com.example.androidtestproject.data.repository.preference.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Neha Dessai on 30-03-2023.
 */

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {
    /*Shared Preference related*/
    //Create shared preference, to inject in shared preference helper method
    @Singleton
    @Provides
    internal fun provideSharedPrefs(application: Application): SharedPreferences {
        return application.getSharedPreferences(BuildConfig.APP_PREFS, Context.MODE_PRIVATE)
    }

    //Inject the created shared preference above and inject in helper
    @Singleton
    @Provides
    internal fun provideSharedPrefsHelper(preferences: SharedPreferences): SharedPrefManager {
        return SharedPrefManager(preferences)
    }

    /* Since SharedPrefsHelper implements PreferencesHelper, we pass SharedPrefsHelper as argument.
     * Also PreferencesHelper will be the final Object for injections.
     * */
    @Singleton
    @Provides
    internal fun providePreferencesHelper(sharedPrefsHelper: SharedPrefManager): PreferenceHelper {
        return sharedPrefsHelper
    }
}