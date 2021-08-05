package com.example.successcontribution.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OperationStatusModel(
    @SerializedName("operationResult")
    @Expose
    val operationResult: String,

    @SerializedName("operationName")
    @Expose
    val operationName: String
)
