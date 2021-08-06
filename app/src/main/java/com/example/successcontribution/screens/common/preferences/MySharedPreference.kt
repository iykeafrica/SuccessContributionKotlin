package com.example.successcontribution.screens.common.preferences

import android.content.Context
import com.example.successcontribution.shared.Constant

class MySharedPreference(applicationContext: Context) {

    val preference = applicationContext.getSharedPreferences(Constant.MY_PREF, 0)!!
    val editor = preference.edit()!!

}