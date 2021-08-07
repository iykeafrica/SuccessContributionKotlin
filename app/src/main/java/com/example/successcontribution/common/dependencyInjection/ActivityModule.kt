package com.example.successcontribution.common.dependencyInjection

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val appCompatActivity: AppCompatActivity,
    private val appComponent: AppComponent
) {

    private val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(appCompatActivity)
    }

    private val activity: Activity = appCompatActivity

    @Provides
    fun activity() = activity

    @Provides
    fun screensNavigator() = screensNavigator

    @Provides
    fun appCompatActivity() = appCompatActivity

    @Provides
    fun fragmentManager() = appCompatActivity.supportFragmentManager

    @Provides
    fun application() = appComponent.application()

    @Provides
    fun applicationContext() = appComponent.applicationContext()

    @Provides
    fun mySharedPreference() = appComponent.mySharedPreference()

    @Provides
    fun successContributionsApi() = appComponent.successContributionsApi()

}