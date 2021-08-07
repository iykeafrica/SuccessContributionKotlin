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
    private val activityComponent: ActivityComponent
) {

    @Provides
    fun appCompactActivity() = activityComponent.appCompactActivity()

    @Provides
    fun activity() = activityComponent.activity()

    @Provides
    fun application() = activityComponent.application()

    @Provides
    fun applicationContext() = activityComponent.applicationContext()

    @Provides
    fun successContributionsApi() = activityComponent.successContributionsApi()

    @Provides
    fun fragmentManager() = activityComponent.fragmentManager()

    @Provides
    fun mySharedPreference() = activityComponent.mySharedPreference()

    @Provides
    fun screensNavigator() = activityComponent.screensNavigator()

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