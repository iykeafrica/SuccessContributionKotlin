package com.example.successcontribution.screens.common.viewmvc

import android.app.Activity
import android.view.ViewGroup
import com.example.successcontribution.screens.dashboard.DashBoardViewMvc
import com.example.successcontribution.screens.login.LoginViewMvc

class ViewMvcFactory(val activity: Activity) {

    fun newLoginViewMvcFactory(parent: ViewGroup?) : LoginViewMvc {
        return LoginViewMvc(activity, parent)
    }

    fun newDashBoardViewMvcFactory(parent: ViewGroup?) : DashBoardViewMvc {
        return DashBoardViewMvc(activity, parent)
    }
}