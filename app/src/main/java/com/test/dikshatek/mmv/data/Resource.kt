package com.test.dikshatek.mmv.data

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: String? = null, val totalPages: Int? = 1) {
    class Success<T>(data: T, message: String, totalPages: Int) : Resource<T>(data, message, totalPages = totalPages)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, code: String, data: T? = null) : Resource<T>(data, message, code)
    class Empty<T>(message: String, data: T? = null) : Resource<T>(data, message)
}