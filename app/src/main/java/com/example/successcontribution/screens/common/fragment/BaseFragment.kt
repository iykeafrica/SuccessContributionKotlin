package com.example.successcontribution.screens.common.fragment

import androidx.fragment.app.Fragment
import com.example.successcontribution.common.dependencyInjection.DaggerPresentationComponent
import com.example.successcontribution.common.dependencyInjection.Injector
import com.example.successcontribution.common.dependencyInjection.PresentationModule
import com.example.successcontribution.screens.common.activity.BaseActivity


open class BaseFragment: Fragment() {

    private val presentationComponent by lazy {
        DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule((requireActivity() as BaseActivity).activityComponent))
            .build()
    }

    protected val injector get() = Injector(presentationComponent)
}