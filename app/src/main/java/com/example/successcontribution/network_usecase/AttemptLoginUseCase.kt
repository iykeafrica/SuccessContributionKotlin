package com.example.successcontribution.network_usecase

import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AttemptLoginUseCase {
    private var retrofit: Retrofit
    private var successContributionsApi: SuccessContributionsApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        successContributionsApi = retrofit.create(SuccessContributionsApi::class.java)
    }

    fun successContributionsApi() : SuccessContributionsApi {
        return successContributionsApi
    }

}