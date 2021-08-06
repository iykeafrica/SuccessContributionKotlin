package com.example.successcontribution.common.dependencyInjection

import com.example.successcontribution.screens.dashboard.DashBoardActivity
import com.example.successcontribution.screens.list_users.ListUsersActivity
import com.example.successcontribution.screens.login.LoginFragment

class Injector(private val compositionRoot: PresentationCompositionRoot) {

    fun inject(fragment: LoginFragment) {
        fragment.dialogsNavigator = compositionRoot.dialogsNavigator
        fragment.screensNavigator = compositionRoot.screensNavigator
        fragment.mySharedPreference = compositionRoot.mySharedPreference
        fragment.attemptLoginUseCase = compositionRoot.attemptLoginUseCase
    }

    fun inject(activity: DashBoardActivity) {
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.screensNavigator = compositionRoot.screensNavigator
        activity.mySharedPreference = compositionRoot.mySharedPreference
    }

    fun inject(activity: ListUsersActivity) {
        activity.dialogsNavigator = compositionRoot.dialogsNavigator
        activity.screensNavigator = compositionRoot.screensNavigator
        activity.mySharedPreference = compositionRoot.mySharedPreference
        activity.fetchUsersUseCase = compositionRoot.fetchUsersUseCase
    }
}