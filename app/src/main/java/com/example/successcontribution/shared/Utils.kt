package com.example.successcontribution.shared

object Utils {
    fun isNumber(string: String): Boolean {
        for (element in string) {
            if (!Character.isDigit(element)) {
                return false
            }
        }
        return true
    }
}