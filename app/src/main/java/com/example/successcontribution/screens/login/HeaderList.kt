package com.example.successcontribution.screens.login

import com.example.successcontribution.shared.Constant
import okhttp3.Headers

fun authorizationHeader(headerList: Headers): String {
    return headerList[Constant.AUTHORIZATION_HEADER_STRING]!!
}

fun userId(headerList: Headers): String {
    return headerList[Constant.USER_ID]!!
}

fun loginRole(headerList: Headers): String {
    return headerList[Constant.LOGIN_ROLE]!!
}

fun balance(headerList: Headers): String {
    return headerList[Constant.SAVINGS_BALANCE]!!
}

fun firstName(headerList: Headers): String {
    return headerList[Constant.FIRST_NAME]!!
}

fun lastName(headerList: Headers): String {
    return headerList[Constant.LAST_NAME]!!
}
