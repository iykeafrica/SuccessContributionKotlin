package com.example.successcontribution.common.composition

import android.app.Activity
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    val activity: Activity,
    private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val successContributionsApi get() = appCompositionRoot.successContributionsApi

    val attemptLoginUseCase: AttemptLoginUseCase get() =  AttemptLoginUseCase(successContributionsApi)

}