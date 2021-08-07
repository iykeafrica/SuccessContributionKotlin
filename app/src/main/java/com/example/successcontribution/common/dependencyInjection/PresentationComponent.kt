package com.example.successcontribution.common.dependencyInjection

import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.network_usecase.FetchUsersUseCase
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.screens.common.viewmvc.ViewMvcFactory
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun mySharedPreference(): MySharedPreference

    fun screensNavigator(): ScreensNavigator

    fun dialogsNavigator(): DialogsNavigator

    fun attemptLoginUseCase(): AttemptLoginUseCase

    fun fetchUsersUseCase(): FetchUsersUseCase

    fun viewMvcFactory(): ViewMvcFactory
}