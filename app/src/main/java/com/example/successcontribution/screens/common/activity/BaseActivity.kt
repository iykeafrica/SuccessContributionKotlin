package com.example.successcontribution.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.MyApplication
import com.example.successcontribution.common.dependencyInjection.*

open class BaseActivity : AppCompatActivity() {
    private val appCompositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this, appCompositionRoot))
            .build()
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}