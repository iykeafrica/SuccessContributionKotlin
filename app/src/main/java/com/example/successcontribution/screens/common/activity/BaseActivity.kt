package com.example.successcontribution.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.MyApplication
import com.example.successcontribution.common.composition.ActivityCompositionRoot
import com.example.successcontribution.common.composition.AppCompositionRoot
import com.example.successcontribution.screens.common.preferences.MySharedPreference

open class BaseActivity: AppCompatActivity() {
    private val appCompositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot
//    val myPreferences: MySharedPreference get() = MySharedPreference(applicationContext)

    val compositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }
}