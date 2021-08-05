package com.example.successcontribution.networking

import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.model.response.UserRest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SuccessContributionsApi {

    @POST("/success-contributions/users/login")
    suspend fun login(@Body loginRequestModel: UserLoginRequestModel): Response<ResponseBody>

    @GET("/success-contributions/users")
    suspend fun getUsers(@Query("page") page: Int, @Query("limit") limit: Int) : Response<List<UserRest>>
}