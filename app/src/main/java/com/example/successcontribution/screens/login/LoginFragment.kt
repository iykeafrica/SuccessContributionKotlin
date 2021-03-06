package com.example.successcontribution.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.successcontribution.common.dependencyInjection.Service
import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.fragment.BaseFragment
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LAST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.USER_ID_DEFAULT_KEY
import kotlinx.coroutines.*
import okhttp3.Headers

class LoginFragment : BaseFragment(), LoginViewMvc.Listener {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var loginViewMvc: LoginViewMvc

    @field:Service private lateinit var mySharedPreference: MySharedPreference
    @field:Service private lateinit var attemptLoginUseCase: AttemptLoginUseCase
    @field:Service private lateinit var screensNavigator: ScreensNavigator
    @field:Service private lateinit var dialogsNavigator: DialogsNavigator

    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewMvc = compositionRoot.viewMvcFactory.newLoginViewMvcFactory(container)
        return loginViewMvc.rootView
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
                when (val result = attemptLoginUseCase.attemptLogin(userLoginRequestModel)) {
                    is AttemptLoginUseCase.Result.Success -> {
                        onAttemptSuccess(result.headerList)
                    }
                    is AttemptLoginUseCase.Result.Failure -> {
                        onAttemptFail()
                    }
                }
            } finally {
                loginViewMvc.hideProgressIndication()
            }
        }
    }

    private fun onAttemptSuccess(headerList: Headers) {
        mySharedPreference.storeValue(AUTHORIZATION_TOKEN_DEFAULT_KEY, authorizationHeader(headerList))
        mySharedPreference.storeValue(USER_ID_DEFAULT_KEY, userId(headerList))
        mySharedPreference.storeValue(LOGIN_ROLE_KEY, loginRole(headerList))
        mySharedPreference.storeValue(FIRST_NAME_KEY, firstName(headerList))
        mySharedPreference.storeValue(LAST_NAME_KEY, lastName(headerList))

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