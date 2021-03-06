package com.example.successcontribution.common.dependencyInjection

import androidx.fragment.app.FragmentManager
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.network_usecase.FetchUsersUseCase
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.viewmvc.ViewMvcFactory

class PresentationCompositionRoot(
    private val activityCompositionRoot: ActivityCompositionRoot
) {

    private val activity get() = activityCompositionRoot.activity

    private val application get() = activityCompositionRoot.application

    private val applicationContext = activityCompositionRoot.applicationContext

    private val successContributionsApi get() = activityCompositionRoot.successContributionsApi

    private val fragmentManager: FragmentManager get() = activityCompositionRoot.fragmentManager

    val mySharedPreference get() = activityCompositionRoot.mySharedPreference

    val screensNavigator: ScreensNavigator get() = activityCompositionRoot.screensNavigator

    val dialogsNavigator: DialogsNavigator get() = DialogsNavigator(fragmentManager)

    val attemptLoginUseCase: AttemptLoginUseCase get() =  AttemptLoginUseCase(successContributionsApi)

    val fetchUsersUseCase: FetchUsersUseCase get() = FetchUsersUseCase(successContributionsApi)

    val viewMvcFactory get() = ViewMvcFactory(activity)
}