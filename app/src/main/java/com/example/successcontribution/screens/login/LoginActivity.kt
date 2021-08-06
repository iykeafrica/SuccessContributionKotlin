package com.example.successcontribution.screens.login

import android.content.SharedPreferences
import android.os.Bundle
import com.example.successcontribution.R
import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LAST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY
import kotlinx.coroutines.*
import okhttp3.Headers

import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.screens.common.preferences.Pref
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.activity.BaseActivity
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.layout_frame, LoginFragment())
                .commit()
        }

    }
}