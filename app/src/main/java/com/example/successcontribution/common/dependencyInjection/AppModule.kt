package com.example.successcontribution.common.dependencyInjection

import android.app.Application
import android.content.Context
import androidx.annotation.UiThread
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.shared.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@UiThread
class AppModule(private val application: Application) {
    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val successContributionsApi: SuccessContributionsApi by lazy {
        retrofit.create(SuccessContributionsApi::class.java)
    }

    private val applicationContext = application.applicationContext

    private val mySharedPreference by lazy {
        MySharedPreference(applicationContext)
    }

    @Provides
    fun application(): Application = application

    @Provides
    fun applicationContext(): Context = applicationContext

    @Provides
    fun successContributionsApi() = successContributionsApi

    @Provides
    fun mySharedPreference() = mySharedPreference

}