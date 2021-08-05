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
import androidx.appcompat.app.AlertDialog

import com.example.successcontribution.screens.login.LoginActivity

class DashBoardActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var preferences: SharedPreferences
    private var userRole: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = applicationContext.getSharedPreferences(Constant.MY_PREF, 0)

        val intent = intent
        if (intent.hasExtra(LOGIN_ROLE_KEY)){
            checkUserSignIn();
        }

    }

    override fun onResume() {
        super.onResume()
        binding.userSignedIn.visibility = View.GONE
        binding.admin.visibility = View.GONE
        checkUserSignIn()
    }

    private fun checkUserSignIn() {
        val role = intent.getStringExtra((LOGIN_ROLE_KEY))

        if (role.equals(USER) || USER == preferences.getString(LOGIN_ROLE_KEY, "")) {
            binding.userSignedIn.visibility = View.VISIBLE
            binding.name.text = preferences.getString(FIRST_NAME_KEY, "")

            userRole = preferences.getString(LOGIN_ROLE_KEY, "")

//            checkLoanApplications()
//            requestLoan()
//            profile()
//            listUsers()
//            guaranteeLoan()

        } else {
            binding.admin.visibility = View.VISIBLE
            binding.name.text = preferences.getString(FIRST_NAME_KEY, "")
//            goToAdminPage()
        }
    }

    private fun checkLoanApplications() {
        TODO("Not yet implemented")
    }

    private fun requestLoan() {
        TODO("Not yet implemented")
    }

    private fun listUsers() {
        TODO("Not yet implemented")
    }

    private fun profile() {
        TODO("Not yet implemented")
    }

    private fun guaranteeLoan() {
        TODO("Not yet implemented")
    }

    private fun goToAdminPage() {
        TODO("Not yet implemented")
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

}