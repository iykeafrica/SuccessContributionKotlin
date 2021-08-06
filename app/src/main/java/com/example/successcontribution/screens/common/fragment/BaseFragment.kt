package com.example.successcontribution.screens.common.fragment

import androidx.fragment.app.Fragment
import com.example.successcontribution.common.composition.ActivityCompositionRoot
import com.example.successcontribution.screens.common.activity.BaseActivity


open class BaseFragment: Fragment() {
    protected val compositionRoot: ActivityCompositionRoot get() = (requireActivity() as BaseActivity).compositionRoot
}