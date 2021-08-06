package com.example.successcontribution.common.composition

import androidx.annotation.UiThread
import com.example.successcontribution.MyApplication
import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.shared.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@UiThread
class AppCompositionRoot {
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

    val successContributionsApi: SuccessContributionsApi by lazy {
        retrofit.create(SuccessContributionsApi::class.java)
    }

}