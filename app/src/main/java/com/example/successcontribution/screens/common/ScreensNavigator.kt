package com.example.successcontribution.screens.common

import android.app.Activity
import android.content.Context
import com.example.successcontribution.screens.dashboard.DashBoardActivity

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
}