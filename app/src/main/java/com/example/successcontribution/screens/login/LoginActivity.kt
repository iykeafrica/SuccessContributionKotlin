package com.example.successcontribution.screens.login

import android.os.Bundle
import com.example.successcontribution.R

import com.example.successcontribution.screens.common.activity.BaseActivity


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.layout_frame, LoginFragment())
                .commit()
        }

    }
}