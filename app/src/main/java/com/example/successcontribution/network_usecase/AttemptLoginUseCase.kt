package com.example.successcontribution.network_usecase

import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.networking.SuccessContributionsApi
import com.example.successcontribution.shared.Constant
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AttemptLoginUseCase(private val successContributionsApi: SuccessContributionsApi) {

    sealed class Result {
        data class Success(val headerList: Headers) : Result()
        object Failure : Result()
    }

    suspend fun attemptLogin(userLoginRequestModel: UserLoginRequestModel): Result {
        return withContext(Dispatchers.IO){
            try {
                val response = successContributionsApi.login(userLoginRequestModel)
                if (response.isSuccessful && response.body() != null) {
                    val headerList: Headers = response.headers()
                    return@withContext Result.Success(headerList)
                } else {
                    return@withContext Result.Failure
                }

            } catch (e: Throwable) {
                if (e !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw e
                }
            }
        }
    }

}