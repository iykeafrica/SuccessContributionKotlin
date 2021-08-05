package com.example.successcontribution.screens.login

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LAST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.MY_PREF
import com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY
import kotlinx.coroutines.*
import okhttp3.Headers

import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.screens.common.Pref
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.dialogs.ServerErrorDialogFragment


class LoginActivity : AppCompatActivity(), LoginViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var loginViewMvc: LoginViewMvc
    private lateinit var successContributionsApi: SuccessContributionsApi
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewMvc = LoginViewMvc(this, null)
        setContentView(loginViewMvc.rootView)

        successContributionsApi = AttemptLoginUseCase().successContributionsApi()
        screensNavigator = ScreensNavigator(this)
        dialogsNavigator = DialogsNavigator(supportFragmentManager)
        preferences = applicationContext.getSharedPreferences(MY_PREF, 0)
        editor = preferences.edit()
    }

    override fun submit() {
        loginViewMvc.hideKeyboard()
        loginViewMvc.getCredentials()

        username = loginViewMvc.username
        password = loginViewMvc.password

        if (username.isNotEmpty() && password.isNotEmpty())
            setCredentials(username, password)
    }

    private fun setCredentials(username: String, password: String) {
        attemptLogin(Credentials.signInCredentials(username, password))
    }

    private fun attemptLogin(userLoginRequestModel: UserLoginRequestModel) {
        coroutineScope.launch {
            loginViewMvc.showProgressIndication()

            try {
                val response = successContributionsApi.login(userLoginRequestModel)
                if (response.isSuccessful) {
                    val headerList: Headers = response.headers()
                    onAttemptSuccess(headerList)
                } else {
                    onAttemptFail()
                }

            } catch (e: Throwable) {
                if (e != CancellationException())
                    onAttemptFail()
            } finally {
                loginViewMvc.hideProgressIndication()
            }
        }
    }

    private fun onAttemptSuccess(headerList: Headers) {

        Pref.storeValue(editor, AUTHORIZATION_TOKEN_DEFAULT_KEY, authorizationHeader(headerList))
        Pref.storeValue(editor, USER_ID_DEFAULT_KEY, userId(headerList))
        Pref.storeValue(editor, LOGIN_ROLE_KEY, loginRole(headerList))
        Pref.storeValue(editor, FIRST_NAME_KEY, firstName(headerList))
        Pref.storeValue(editor, LAST_NAME_KEY, lastName(headerList))

        loginViewMvc.loginSuccess()
        loginViewMvc.clearCredentials()

        screensNavigator.loginToDashBoard(loginRole(headerList), balance(headerList), firstName(headerList))
    }

    private fun onAttemptFail() {
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onStart() {
        loginViewMvc.registerListener(this)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        loginViewMvc.unregisterListener(this)
    }

}