package com.example.successcontribution.screens.common.fragment

import androidx.fragment.app.Fragment
import com.example.successcontribution.common.dependencyInjection.Injector
import com.example.successcontribution.common.dependencyInjection.PresentationCompositionRoot
import com.example.successcontribution.screens.common.activity.BaseActivity


open class BaseFragment: Fragment() {
    protected val compositionRoot: PresentationCompositionRoot by lazy {
        PresentationCompositionRoot((requireActivity() as BaseActivity).activityCompositionRoot)
    }

    protected val injector get() = Injector(compositionRoot)
}