package com.example.successcontribution.screens.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.successcontribution.screens.dashboard.DashBoardActivity
import com.example.successcontribution.screens.list_users.ListUsersActivity

class ScreensNavigator(val activity: Activity) {

    fun loginToDashBoard(
        loginRole: String,
        balance: String,
        firstName: String
    ) {
        DashBoardActivity.loadDashboard(activity, loginRole, balance, firstName)
    }

    fun onNavigateBack() {
        activity.onBackPressed()
    }

    fun navigateToListUsers() {
        activity.startActivity(Intent(activity, ListUsersActivity::class.java))
    }
}