package com.example.successcontribution.common.dependencyInjection

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    
    fun screensNavigator(): ScreensNavigator

    fun appCompactActivity(): AppCompatActivity

    fun activity(): Activity
    
    fun application(): Application

    fun applicationContext(): Context

    fun mySharedPreference(): MySharedPreference
    
    fun fragmentManager(): FragmentManager

    fun successContributionsApi(): SuccessContributionsApi
}