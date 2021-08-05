package com.example.successcontribution.screens.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.successcontribution.databinding.ActivityDashBoardBinding
import com.example.successcontribution.screens.login.LoginViewMvc
import com.example.successcontribution.shared.Constant
import androidx.core.content.ContextCompat.startActivity


class DashBoardViewMvc(
    private val activity: AppCompatActivity,
    private val parent: ViewGroup?
) {

    private val binding: ActivityDashBoardBinding = ActivityDashBoardBinding.inflate(activity.layoutInflater, parent, false)
    val rootView = binding.root
    private var userRole: String? = ""

    interface Listener {
        fun checkLoanApplications()
        fun requestLoan()
        fun profile()
        fun listUsers()
        fun guaranteeLoan()
        fun goToAdminPage()
    }

    private val listeners = HashSet<Listener>()

    fun registerListener(listener: Listener){
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun hideUserRole() {
        binding.userSignedIn.visibility = View.GONE
        binding.admin.visibility = View.GONE
    }

    fun checkUserSignIn(intent: Intent, preferences: SharedPreferences) {
        val role = intent.getStringExtra((Constant.LOGIN_ROLE_KEY))

        if (role.equals(Constant.USER) || Constant.USER == preferences.getString(Constant.LOGIN_ROLE_KEY, "")) {
            binding.userSignedIn.visibility = View.VISIBLE
            binding.name.text = preferences.getString(Constant.FIRST_NAME_KEY, "")

            userRole = preferences.getString(Constant.LOGIN_ROLE_KEY, "")

            checkLoanApplications()
            requestLoan()
            profile()
            listUsers()
            guaranteeLoan()

        } else {
            binding.admin.visibility = View.VISIBLE
            binding.name.text = preferences.getString(Constant.FIRST_NAME_KEY, "")

            goToAdminPage()
        }
    }

    private fun checkLoanApplications() {
        binding.loanApplications.setOnClickListener { v ->
            for (listener in listeners) {
                listener.checkLoanApplications()
            }
        }
    }

    private fun requestLoan() {
        binding.requestLoan.setOnClickListener{
            for (listener in listeners) {
                listener.requestLoan()
            }
        }
    }

    private fun listUsers() {
        binding.profile.setOnClickListener{
            for (listener in listeners) {
                listener.listUsers()
            }
        }
    }

    private fun profile() {
        binding.profile.setOnClickListener{
            for (listener in listeners) {
                listener.profile()
            }
        }
    }

    private fun guaranteeLoan() {
        binding.guaranteeLoan.setOnClickListener {
            for (listener in listeners) {
                listener.guaranteeLoan()
            }
        }
    }

    private fun goToAdminPage() {
        binding.admin.setOnClickListener {
            for (listener in listeners) {
                listener.goToAdminPage()
            }
        }
    }


}