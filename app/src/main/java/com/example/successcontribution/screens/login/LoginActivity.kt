package com.example.successcontribution.screens.login

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant.AUTHORIZATION_HEADER_STRING
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import com.example.successcontribution.shared.Constant.BASE_URL
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
import com.example.successcontribution.shared.Utils
import kotlinx.coroutines.*
import okhttp3.Headers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.successcontribution.screens.dashboard.DashBoardActivity

import android.content.Intent
import com.example.successcontribution.screens.common.dialogs.ServerErrorDialogFragment
import com.example.successcontribution.shared.Constant.SAVINGS_BALANCE_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity(), LoginViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var retrofit: Retrofit
    private lateinit var successContributionsApi: SuccessContributionsApi

    private lateinit var preferences: SharedPreferences
    private lateinit var loginViewMvc: LoginViewMvc
    private lateinit var username: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewMvc = LoginViewMvc(this, null)

        setContentView(loginViewMvc.rootView)

        preferences = applicationContext.getSharedPreferences(MY_PREF, 0)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        successContributionsApi = retrofit.create(SuccessContributionsApi::class.java)
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

        val userLoginRequestModel = UserLoginRequestModel()
        userLoginRequestModel.setPassword(password)

        if (Utils.isNumber(username) && username.length >= 10 && username.length < 12)
            userLoginRequestModel.setPassword(username).toString()
        if (username.contains('@'))
            userLoginRequestModel.setEmail(username)
        if (Utils.isNumber(username) && username.length <= 5)
            userLoginRequestModel.setSapNo(username)
        if (username.length == 30)
            userLoginRequestModel.setUserId(username)

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
        val authorization: String = headerList[AUTHORIZATION_HEADER_STRING]!!
        val userId: String = headerList[USER_ID]!!
        val loginRole: String = headerList[LOGIN_ROLE]!!
        val balance: String = headerList[SAVINGS_BALANCE]!!
        val firstName: String = headerList[FIRST_NAME]!!
        val lastName: String = headerList[LAST_NAME]!!

        preferences.edit().putString(AUTHORIZATION_TOKEN_DEFAULT_KEY, authorization).apply()
        preferences.edit().putString(USER_ID_DEFAULT_KEY, userId).apply()
        preferences.edit().putString(LOGIN_ROLE_KEY, loginRole).apply()
        preferences.edit().putString(FIRST_NAME_KEY, firstName).apply()
        preferences.edit().putString(LAST_NAME_KEY, lastName).apply()

        loginViewMvc.loginSuccess()
        loginViewMvc.clearCredentials()

        val intent = Intent(this, DashBoardActivity::class.java)
        intent.putExtra(LOGIN_ROLE_KEY, loginRole)
        intent.putExtra(SAVINGS_BALANCE_KEY, balance)
        intent.putExtra(FIRST_NAME_KEY, firstName)
        startActivity(intent)
    }

    private fun onAttemptFail() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    override fun submit() {
        loginViewMvc.hideKeyboard()
        loginViewMvc.getCredentials()

        username = loginViewMvc.username
        password = loginViewMvc.password

        if (username.isNotEmpty() && password.isNotEmpty())
            setCredentials(username, password)
    }

}