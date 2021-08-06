package com.example.successcontribution.networking

import com.example.successcontribution.model.request.UserLoginRequestModel
import com.example.successcontribution.model.response.UserRest
import com.example.successcontribution.shared.Constant.AUTHORIZATION_HEADER_STRING
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SuccessContributionsApi {

    @POST("/success-contributions/users/login")
    suspend fun login(@Body loginRequestModel: UserLoginRequestModel): Response<ResponseBody>

    @GET("/success-contributions/users")
    suspend fun getUsers(
        @Header(AUTHORIZATION_HEADER_STRING) authorization: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int) : Response<List<UserRest>>
}