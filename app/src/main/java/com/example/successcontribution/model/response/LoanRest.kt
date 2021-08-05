package com.example.successcontribution.model.response

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "loans")
data class LoanRest(

    @NonNull @PrimaryKey @SerializedName("loanId") @Expose val loanId: String,

    @SerializedName("name") @Expose val name: String,

    @SerializedName("amount") @Expose val amount: String,

    @SerializedName("reason") @Expose val reason: String,

    @SerializedName("requestDate") @Expose val requestDate: Long,

    @SerializedName("repaymentDate") @Expose val repaymentDate: Long,

    @SerializedName("guarantorOne") @Expose val guarantorOne: String,

    @SerializedName("guarantorTwo") @Expose val guarantorTwo: String,

    @SerializedName("guarantorOneConfirmation") @Expose val guarantorOneConfirmation: String,

    @SerializedName("guarantorTwoConfirmation") @Expose val guarantorTwoConfirmation: String,

    @SerializedName("status") @Expose val status: String,

    @SerializedName("officialOne") @Expose val officialOne: String,

    @SerializedName("officialTwo") @Expose val officialTwo: String,

    @SerializedName("officialThree") @Expose val officialThree: String,

    @SerializedName("president") @Expose val president: String,

    @SerializedName("statusDate") @Expose val statusDate: Long,

    @SerializedName("editable") @Expose val isEditable: Boolean

) : Parcelable
