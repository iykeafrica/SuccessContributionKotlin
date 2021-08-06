package com.example.successcontribution.common.composition

import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppCompositionRoot {
    private val loggingInterceptor =
        run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
        }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val successContributionsApi: SuccessContributionsApi = retrofit.create(
        SuccessContributionsApi::class.java)

    val attemptLoginUseCase: AttemptLoginUseCase = AttemptLoginUseCase(successContributionsApi)

}