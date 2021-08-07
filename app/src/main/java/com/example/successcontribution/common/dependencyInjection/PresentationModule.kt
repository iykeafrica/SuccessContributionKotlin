package com.example.successcontribution.common.dependencyInjection

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.network_usecase.FetchUsersUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.viewmvc.ViewMvcFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(
    private val activityCompositionRoot: ActivityCompositionRoot
) {

    @Provides
    fun activity() = activityCompositionRoot.activity

    @Provides
    fun application() = activityCompositionRoot.application

    @Provides
    fun applicationContext() = activityCompositionRoot.applicationContext

    @Provides
    fun successContributionsApi() = activityCompositionRoot.successContributionsApi

    @Provides
    fun fragmentManager() = activityCompositionRoot.fragmentManager


    @Provides
    fun mySharedPreference() = activityCompositionRoot.mySharedPreference

    @Provides
    fun screensNavigator() = activityCompositionRoot.screensNavigator

    @Provides
    fun dialogsNavigator(fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

    @Provides
    fun attemptLoginUseCase(successContributionsApi: SuccessContributionsApi) =
        AttemptLoginUseCase(successContributionsApi)

    @Provides
    fun fetchUsersUseCase(successContributionsApi: SuccessContributionsApi) =
        FetchUsersUseCase(successContributionsApi)

    @Provides
    fun viewMvcFactory(activity: Activity) = ViewMvcFactory(activity)
}