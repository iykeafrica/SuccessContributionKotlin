package com.example.successcontribution.screens.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.successcontribution.MyApplication
import com.example.successcontribution.common.composition.AppCompositionRoot

open class BaseActivity: AppCompatActivity() {
    val compositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot
}