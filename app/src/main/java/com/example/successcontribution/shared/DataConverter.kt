package com.example.successcontribution.shared

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.example.successcontribution.model.response.LoanRest

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverter {
    @TypeConverter
    fun fromString(value: String?): List<LoanRest?>? { //While reading data back from Room Database,
        val listType: Type = object : TypeToken<List<LoanRest?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLoanList(loans: List<LoanRest?>?): String? { //returns String representation of List<LoanRest> loans in UserRest to be stored in DB
        val gson = Gson()
        return gson.toJson(loans)
    }
}