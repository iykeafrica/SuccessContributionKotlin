package com.example.successcontribution.common.dependencyInjection

import android.app.Application
import android.content.Context
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun application(): Application

    fun applicationContext(): Context

    fun successContributionsApi(): SuccessContributionsApi

    fun mySharedPreference(): MySharedPreference
}