package com.example.successcontribution.common.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val application: Application = appCompositionRoot.application

    val applicationContext: Context = application.applicationContext

    val mySharedPreference get() = appCompositionRoot.mySharedPreference

    val fragmentManager: FragmentManager get() = activity.supportFragmentManager

    val successContributionsApi get() = appCompositionRoot.successContributionsApi

}