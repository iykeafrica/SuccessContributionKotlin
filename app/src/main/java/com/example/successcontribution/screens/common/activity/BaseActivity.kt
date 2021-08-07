package com.example.successcontribution.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.MyApplication
import com.example.successcontribution.common.dependencyInjection.*

open class BaseActivity : AppCompatActivity() {
    private val appCompositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(activityCompositionRoot))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}