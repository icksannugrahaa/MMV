package com.test.dikshatek.mmv.data.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val code: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}