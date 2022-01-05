package com.example.az.utils

import android.util.Log.d
import retrofit2.Response

suspend fun <T> handleResponse(apiCall: suspend () -> Response<T>): Resource<T> {
    try {
        val result = apiCall()
        val body = result.body()
        if (result.isSuccessful && body != null) {
            d("testing AZ", "handleResponse  $result \n $body")
            body.let {
//                d("testing AZ", "handleResponse  $result \n $body")
                return Resource.Success(body)
            }
        }
        return Resource.Error(null , result.message())
    } catch (e: Exception) {
        return Resource.Error(null , e.message ?: e.toString())
    }
}

