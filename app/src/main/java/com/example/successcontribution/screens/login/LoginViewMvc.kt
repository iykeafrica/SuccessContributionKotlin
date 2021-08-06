package com.example.successcontribution.screens.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.R
import com.example.successcontribution.databinding.ActivityLoginBinding
import com.example.successcontribution.databinding.FragmentLoginBinding
import com.example.successcontribution.screens.common.viewmvc.BaseViewMvc

class LoginViewMvc(
    activity: Activity,
    parent: ViewGroup?
): BaseViewMvc<LoginViewMvc.Listener>(activity) {

    private var binding: FragmentLoginBinding =
        FragmentLoginBinding.inflate(activity.layoutInflater, parent, false)

//    private var binding: ActivityLoginBinding =
//        ActivityLoginBinding.inflate(activity.layoutInflater, parent, false)

    val rootView = binding.root
    var username: String = ""
    var password: String = ""

    interface Listener {
        fun submit()
    }

    init {
        hideOpeningKeyBoard(binding.etUsername)

        binding.btnLogin.setOnClickListener {
            for(listener in listeners){
                listener.submit()
            }
        }
    }

    fun getCredentials() {
        if (binding.etUsername.text.toString().trim().isEmpty() && binding.etPassword.text.toString().isEmpty())
            Toast.makeText(activity, "Username and password are empty", Toast.LENGTH_LONG).show()
        else {
            if (binding.etUsername.text.toString().trim().isEmpty())
                Toast.makeText(activity, "Username is empty", Toast.LENGTH_LONG).show()
            else if (binding.etPassword.text.toString().isEmpty())
                Toast.makeText(activity, "Password is empty", Toast.LENGTH_LONG).show()
            else if (binding.etUsername.text.toString().trim().isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()) {
                username = binding.etUsername.text.toString()
                password = binding.etPassword.text.toString()
            }
        }
    }

    fun clearCredentials() {
        binding.etUsername.setText("")
        binding.etPassword.setText("")
    }

    fun loginSuccess() {
        Toast.makeText(activity, "Login Success!", Toast.LENGTH_SHORT).show()
    }

}