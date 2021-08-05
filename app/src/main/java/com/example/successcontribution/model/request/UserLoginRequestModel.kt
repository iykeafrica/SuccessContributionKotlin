package com.example.successcontribution.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserLoginRequestModel {

    @SerializedName("userId")
    @Expose
    private var userId: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("phoneNo")
    @Expose
    private var phoneNo: String? = null

    @SerializedName("sapNo")
    @Expose
    private var sapNo: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String?) {
        this.phoneNo = phoneNo
    }

    fun getSapNo(): String? {
        return sapNo
    }

    fun setSapNo(sapNo: String?) {
        this.sapNo = sapNo
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}
