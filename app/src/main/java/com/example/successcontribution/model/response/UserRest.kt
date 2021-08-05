package com.example.successcontribution.model.response

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserRest(
    @NonNull
    @PrimaryKey
    @SerializedName("userId")
    @Expose
    val userId: String,

    @SerializedName("depositedFund")
    @Expose
    val depositedFund: String,

    @SerializedName("firstName")
    @Expose
    val firstName: String,

    @SerializedName("lastName")
    @Expose
    val lastName: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("department")
    @Expose
    val department: String,

    @SerializedName("sapNo")
    @Expose
    val sapNo: String,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("phoneNo")
    @Expose
    val phoneNo: String,

    @SerializedName("whatsappNo")
    @Expose
    val whatsappNo: String,

    @SerializedName("fcmToken")
    @Expose
    val fcmToken: String,

    @SerializedName("loanEligibility")
    @Expose
    val loanEligibility: Boolean,

    @SerializedName("role")
    @Expose
    val role: String,

    @SerializedName("loans")
    @Expose
    val loans: List<LoanRest>
)