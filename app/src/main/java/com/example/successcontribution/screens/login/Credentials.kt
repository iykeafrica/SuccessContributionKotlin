package com.example.successcontribution.screens.login

import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.shared.Utils

object Credentials {

    fun signInCredentials(username: String, password: String): UserLoginRequestModel {
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

        return userLoginRequestModel
    }

}