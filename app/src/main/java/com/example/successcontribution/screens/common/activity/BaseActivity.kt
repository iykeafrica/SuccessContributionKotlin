package com.example.successcontribution.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.MyApplication
import com.example.successcontribution.common.dependencyInjection.ActivityCompositionRoot
import com.example.successcontribution.common.dependencyInjection.AppCompositionRoot
import com.example.successcontribution.common.dependencyInjection.PresentationCompositionRoot

open class BaseActivity : AppCompatActivity() {
    private val appCompositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot

    //    val myPreferences: MySharedPreference get() = MySharedPreference(applicationContext)
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    protected val compositionRoot: PresentationCompositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }
}