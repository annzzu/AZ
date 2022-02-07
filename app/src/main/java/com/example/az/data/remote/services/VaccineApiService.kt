package com.example.az.data.remote.services

import com.example.az.domain.model.vaccine.VaccineResponse
import com.example.az.utils.endpoints.ApiEndpoints
import retrofit2.Response
import retrofit2.http.GET


interface VaccineApiService {

    @GET(ApiEndpoints.VACCINE)
    suspend fun getVaccines(): Response<VaccineResponse>

}