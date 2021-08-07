package com.example.successcontribution

import android.app.Application
import com.example.successcontribution.common.dependencyInjection.AppComponent
import com.example.successcontribution.common.dependencyInjection.AppModule
import com.example.successcontribution.common.dependencyInjection.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}