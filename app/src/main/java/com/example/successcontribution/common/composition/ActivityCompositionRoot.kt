package com.example.successcontribution.common.composition

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.screens.common.viewmvc.ViewMvcFactory

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