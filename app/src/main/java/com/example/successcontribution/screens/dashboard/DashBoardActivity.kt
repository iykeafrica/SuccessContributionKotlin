package com.example.successcontribution.screens.dashboard

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.successcontribution.shared.Constant
import com.example.successcontribution.shared.Constant.FIRST_NAME_KEY
import com.example.successcontribution.shared.Constant.LOGIN_ROLE_KEY
import com.example.successcontribution.shared.Constant.USER
import kotlinx.android.synthetic.main.activity_dash_board.*
import android.content.Intent
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.successcontribution.screens.common.activity.BaseActivity
import com.example.successcontribution.screens.common.dialogs.BackPressedDialogFragment
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference

import com.example.successcontribution.screens.login.LoginActivity

private val TAG = DashBoardActivity::class.java.simpleName

class DashBoardActivity : BaseActivity(), DashBoardViewMvc.Listener {

    private lateinit var mySharedPreference: MySharedPreference
    private lateinit var dashBoardViewMvc: DashBoardViewMvc
    private lateinit var dialogsNavigator: DialogsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashBoardViewMvc = DashBoardViewMvc(this, null)
        setContentView(dashBoardViewMvc.rootView)

        mySharedPreference = compositionRoot.mySharedPreference
        dialogsNavigator = compositionRoot.dialogsNavigator

        if (intent.hasExtra(LOGIN_ROLE_KEY)) {
            dashBoardViewMvc.checkUserSignIn(intent, mySharedPreference.preference)
        }
    }

    override fun onResume() {
        super.onResume()
        dashBoardViewMvc.hideUserRole()
        dashBoardViewMvc.checkUserSignIn(intent, mySharedPreference.preference)
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
        dialogsNavigator.showBackPressed()
    }

    override fun checkLoanApplications() {
        Log.d(TAG, "checkLoanApplications: checkLoanApplications")
        val name = mySharedPreference.preference.getString(Constant.LAST_NAME_KEY, "")
        Log.d(TAG, "checkLoanApplications: $name")
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

    companion object {
        fun loadDashboard(context: Context, loginRole: String, balance: String, firstName: String) {
            val intent = Intent( context, DashBoardActivity::class.java)
            intent.putExtra(LOGIN_ROLE_KEY, loginRole)
            intent.putExtra(Constant.SAVINGS_BALANCE_KEY, balance)
            intent.putExtra(FIRST_NAME_KEY, firstName)
            context.startActivity(intent)
        }
    }

}