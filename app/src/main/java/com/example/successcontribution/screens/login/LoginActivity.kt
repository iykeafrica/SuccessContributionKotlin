package com.example.successcontribution.screens.login

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.successcontribution.databinding.ActivityLoginBinding
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
import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.successcontribution.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var binding: ActivityLoginBinding
    private lateinit var retrofit: Retrofit
    private lateinit var successContributionsApi: SuccessContributionsApi
    private lateinit var progressDialog : ProgressDialog
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideOpeningKeyBoard(this, binding.etUsername)

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

        progressDialog = ProgressDialog(this@LoginActivity, R.style.MyAlertDialogStyle)

        successContributionsApi = retrofit.create(SuccessContributionsApi::class.java)

        binding.btnLogin.setOnClickListener{
            getCredentials()
            hideKeyboard(this)
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
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
        if(username.length == 30)
            userLoginRequestModel.setUserId(username)

        attemptLogin(userLoginRequestModel)
    }

    private fun getCredentials() {

        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty() && password.isEmpty())
            Toast.makeText(this, "Username and password are empty", Toast.LENGTH_LONG).show()
        else {
            if (username.isEmpty())
                Toast.makeText(this, "Username is empty", Toast.LENGTH_LONG).show()

            else if (password.isEmpty())
                Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show()

            else if (username.isNotEmpty() && password.isNotEmpty())
                setCredentials(username, password)
        }
    }

    private fun attemptLogin(userLoginRequestModel: UserLoginRequestModel) {
        coroutineScope.launch {
            showProgressIndication(progressDialog)

            try {
                val response  = successContributionsApi.login(userLoginRequestModel)
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
                hideProgressIndication(progressDialog)
            }
        }
    }

    private fun onAttemptSuccess(headerList: Headers) {
        val authorization: String = headerList.get(AUTHORIZATION_HEADER_STRING)!!
        val userId: String = headerList.get(USER_ID)!!
        val loginRole: String = headerList.get(LOGIN_ROLE)!!
        val balance: String = headerList.get(SAVINGS_BALANCE)!!
        val firstName: String = headerList.get(FIRST_NAME)!!
        val lastName: String = headerList.get(LAST_NAME)!!

        preferences.edit().putString(AUTHORIZATION_TOKEN_DEFAULT_KEY, authorization).apply()
        preferences.edit().putString(USER_ID_DEFAULT_KEY, userId).apply()
        preferences.edit().putString(LOGIN_ROLE_KEY, loginRole).apply()
        preferences.edit().putString(FIRST_NAME_KEY, firstName).apply()
        preferences.edit().putString(LAST_NAME_KEY, lastName).apply()

        Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()

        binding.etUsername.setText("")
        binding.etPassword.setText("")

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

    private fun showProgressIndication(progressDialog: ProgressDialog) {
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
    }

    private fun hideProgressIndication(progressDialog: ProgressDialog) {
        progressDialog.dismiss()
    }

    private fun hideOpeningKeyBoard(activity: Activity, editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setOnClickListener {
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.requestFocus()
            val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}