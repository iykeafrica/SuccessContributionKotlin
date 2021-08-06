package com.example.successcontribution.network_usecase

import com.example.successcontribution.model.response.UserRest
import com.example.successcontribution.networking.SuccessContributionsApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchUsersUseCase(private val successContributionsApi: SuccessContributionsApi) {

    sealed class Result {
        data class Success(val users: List<UserRest>) : Result()
        object Failure : Result()
    }

    suspend fun fetchUsers(authorization: String, page: Int, limit: Int): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = successContributionsApi.getUsers(authorization, page, limit)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext Result.Success(result)
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