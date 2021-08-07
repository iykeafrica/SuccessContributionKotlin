package com.example.successcontribution.common.dependencyInjection

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    private val appCompatActivity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(appCompatActivity)
    }

    val activity: Activity = appCompatActivity

    val application: Application = appCompositionRoot.application

    val applicationContext: Context = application.applicationContext

    val mySharedPreference get() = appCompositionRoot.mySharedPreference

    val fragmentManager: FragmentManager get() = appCompatActivity.supportFragmentManager

    val successContributionsApi get() = appCompositionRoot.successContributionsApi

}