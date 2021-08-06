package com.example.successcontribution.screens.common.preferences

import android.content.Context
import com.example.successcontribution.shared.Constant

class MySharedPreference(applicationContext: Context) {

    val preference = applicationContext.getSharedPreferences(Constant.MY_PREF, 0)!!
    private val editor = preference.edit()!!

    fun storeValue(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getStoredString(key: String, defValue: String): String {
        return preference.getString(key, defValue)!!
    }

}