package com.example.az.data.remote.services

import com.example.az.model.nationality.NationalityResponse
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.GET


interface NationalityApiService {

    @GET(ApiEndpoints.NATIONALITY)
    suspend fun getNationalities(): Response<NationalityResponse>

}