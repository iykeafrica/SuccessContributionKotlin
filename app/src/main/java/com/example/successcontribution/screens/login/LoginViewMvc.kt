package com.example.successcontribution.screens.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.R
import com.example.successcontribution.databinding.ActivityLoginBinding

class LoginViewMvc(
    private val activity: AppCompatActivity,
    private val parent: ViewGroup?
) {

    private var binding: ActivityLoginBinding =
        ActivityLoginBinding.inflate(activity.layoutInflater, parent, false)

    private val progressDialog: ProgressDialog = ProgressDialog(activity, R.style.MyAlertDialogStyle)
    val rootView = binding.root
    var username: String = ""
    var password: String = ""

    interface Listener {
        fun submit()
    }

    private val listeners = HashSet<Listener>()

    fun registerListener(listener: Listener){
       listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    init {
        hideOpeningKeyBoard(activity, binding.etUsername)

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

    fun showProgressIndication() {
        progressDialog.setMessage("Authenticating please wait...");
        progressDialog.show();
    }

    fun hideProgressIndication() {
        progressDialog.dismiss()
    }

    fun clearCredentials() {
        binding.etUsername.setText("")
        binding.etPassword.setText("")
    }

    fun loginSuccess() {
        Toast.makeText(activity, "Login Success!", Toast.LENGTH_SHORT).show()
    }

    private fun hideOpeningKeyBoard(activity: Activity, editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setOnClickListener {
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.requestFocus()
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}