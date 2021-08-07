package com.example.successcontribution.common.dependencyInjection

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val appCompatActivity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    private val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(appCompatActivity)
    }

    private val application = appCompositionRoot.application

    private val activity: Activity = appCompatActivity

    @Provides
    fun screensNavigator() = screensNavigator

    @Provides
    fun appCompatActivity() = appCompatActivity

    @Provides
    fun activity() = activity

    @Provides
    fun application() = appCompositionRoot.application

    @Provides
    fun applicationContext(): Context = application.applicationContext

    @Provides
    fun mySharedPreference() = appCompositionRoot.mySharedPreference

    @Provides
    fun fragmentManager() = appCompatActivity.supportFragmentManager

    @Provides
    fun successContributionsApi() = appCompositionRoot.successContributionsApi

}