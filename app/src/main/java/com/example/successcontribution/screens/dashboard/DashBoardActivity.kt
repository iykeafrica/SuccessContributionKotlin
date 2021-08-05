package com.example.successcontribution.screens.dashboard

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.successcontribution.R
import com.example.successcontribution.databinding.ActivityDashBoardBinding
import com.example.successcontribution.screens.common.dialogs.BackPressedDialogFragment
import com.example.successcontribution.screens.common.dialogs.ServerErrorDialogFragment
import com.example.successcontribution.shared.Constant
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.USER
import kotlinx.android.synthetic.main.activity_dash_board.*
import android.content.Intent
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog

import com.example.successcontribution.screens.login.LoginActivity

private val TAG = DashBoardActivity::class.java.simpleName

class DashBoardActivity : AppCompatActivity(), DashBoardViewMvc.Listener {

    private lateinit var preferences: SharedPreferences

    private lateinit var dashBoardViewMvc: DashBoardViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashBoardViewMvc = DashBoardViewMvc(this, null)

        setContentView(dashBoardViewMvc.rootView)

        preferences = applicationContext.getSharedPreferences(Constant.MY_PREF, 0)

        if (intent.hasExtra(LOGIN_ROLE_KEY)) {
            dashBoardViewMvc.checkUserSignIn(intent, preferences);
        }

    }

    override fun onResume() {
        super.onResume()
        dashBoardViewMvc.hideUserRole()
        dashBoardViewMvc.checkUserSignIn(intent, preferences)
    }

    override fun onStart() {
        dashBoardViewMvc.registerListener(this)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        dashBoardViewMvc.unregisterListener(this)
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to logout?")
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(this@DashBoardActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun checkLoanApplications() {
        Log.d(TAG, "checkLoanApplications: checkLoanApplications")
    }

    override fun requestLoan() {
        Log.d(TAG, "requestLoan: requestLoan")
    }

    override fun profile() {
        Log.d(TAG, "profile: profile")
    }

    override fun listUsers() {
        Log.d(TAG, "listUsers: listUsers")
    }

    override fun guaranteeLoan() {
        Log.d(TAG, "guaranteeLoan: guaranteeLoan")
    }

    override fun goToAdminPage() {
        Log.d(TAG, "goToAdminPage: goToAdminPage")
    }

}