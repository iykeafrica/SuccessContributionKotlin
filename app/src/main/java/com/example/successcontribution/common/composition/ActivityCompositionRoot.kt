package com.example.successcontribution.common.composition

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference

class ActivityCompositionRoot(
    val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val applicationContext = activity.applicationContext

    val mySharedPreference by lazy {
        MySharedPreference(applicationContext)
    }

    val dialogsNavigator: DialogsNavigator get() = DialogsNavigator(activity.supportFragmentManager)

    private val successContributionsApi get() = appCompositionRoot.successContributionsApi

    val attemptLoginUseCase: AttemptLoginUseCase get() =  AttemptLoginUseCase(successContributionsApi)

}