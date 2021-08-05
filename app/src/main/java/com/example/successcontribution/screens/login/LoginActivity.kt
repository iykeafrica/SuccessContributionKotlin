package com.example.successcontribution.screens.login

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant.AUTHORIZATION_HEADER_STRING
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import com.example.successcontribution.shared.Constant.FIRST_NAME
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LAST_NAME
import com.example.successcontribution.shared.Constant.LAST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.MY_PREF
import com.example.successcontribution.shared.Constant.SAVINGS_BALANCE
import com.example.successcontribution.shared.Constant.USER_ID
import com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY
import kotlinx.coroutines.*
import okhttp3.Headers
import com.example.successcontribution.screens.dashboard.DashBoardActivity

import android.content.Intent
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.screens.common.dialogs.ServerErrorDialogFragment
import com.example.successcontribution.shared.Constant.SAVINGS_BALANCE_KEY


class LoginActivity : AppCompatActivity(), LoginViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var preferences: SharedPreferences
    private lateinit var loginViewMvc: LoginViewMvc

    private lateinit var successContributionsApi: SuccessContributionsApi

    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewMvc = LoginViewMvc(this, null)
        setContentView(loginViewMvc.rootView)

        successContributionsApi = AttemptLoginUseCase().successContributionsApi()

        preferences = applicationContext.getSharedPreferences(MY_PREF, 0)
    }

    override fun submit() {
        loginViewMvc.hideKeyboard()
        loginViewMvc.getCredentials()

        username = loginViewMvc.username
        password = loginViewMvc.password

        if (username.isNotEmpty() && password.isNotEmpty())
            setCredentials(username, password)
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

    private fun setCredentials(username: String, password: String) {
        val userLoginRequestModel = Credentials.signInCredentials(username, password)
        attemptLogin(userLoginRequestModel)
    }

    private fun attemptLogin(userLoginRequestModel: UserLoginRequestModel) {
        coroutineScope.launch {
            showProgressIndication()

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
                hideProgressIndication()
            }
        }
    }


    private fun showProgressIndication() {
        loginViewMvc.showProgressIndication()
    }

    private fun hideProgressIndication() {
        loginViewMvc.hideProgressIndication()
    }

    private fun onAttemptSuccess(headerList: Headers) {

        preferences.edit().putString(AUTHORIZATION_TOKEN_DEFAULT_KEY, HeaderList.authorizationHeader(headerList)).apply()
        preferences.edit().putString(USER_ID_DEFAULT_KEY, HeaderList.userId(headerList)).apply()
        preferences.edit().putString(LOGIN_ROLE_KEY, HeaderList.loginRole(headerList)).apply()
        preferences.edit().putString(FIRST_NAME_KEY, HeaderList.firstName(headerList)).apply()
        preferences.edit().putString(LAST_NAME_KEY, HeaderList.lastName(headerList)).apply()

        loginViewMvc.loginSuccess()
        loginViewMvc.clearCredentials()

        val intent = Intent(this, DashBoardActivity::class.java)
        intent.putExtra(LOGIN_ROLE_KEY, HeaderList.loginRole(headerList))
        intent.putExtra(SAVINGS_BALANCE_KEY, HeaderList.balance(headerList))
        intent.putExtra(FIRST_NAME_KEY, HeaderList.firstName(headerList))
        startActivity(intent)
    }

    private fun onAttemptFail() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }


}