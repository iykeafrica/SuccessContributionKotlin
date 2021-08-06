package com.example.successcontribution

import android.app.Application
import com.example.successcontribution.common.dependencyInjection.AppCompositionRoot

class MyApplication : Application() {
    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot(this)
        super.onCreate()
    }
}