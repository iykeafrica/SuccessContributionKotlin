package com.example.successcontribution.screens.common.preferences

import android.content.SharedPreferences

object Pref {
    fun storeValue(editor: SharedPreferences.Editor, key: String, value: String) {
        editor.putString(key, value).apply()
    }
}