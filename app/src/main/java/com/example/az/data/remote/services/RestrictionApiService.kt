package com.example.az.data.remote.services

import com.example.az.model.restriction.RestrictionResponse
import com.example.az.utils.ApiEndpoints
import retrofit2.Response
import retrofit2.http.*

interface RestrictionApiService {

    //TBS/GVA?nationality=georgian&vaccine=pfizer&transfer=BER
    @GET(ApiEndpoints.RESTRICTION)
    suspend fun getRestriction(
        @Path("from") from: String ,
        @Path("to") to: String ,
        @Query("nationality") nationality: String ,
        @Query("vaccine") vaccine: String ,
        @Query("transfer") transfer: String
    ): Response<RestrictionResponse>

}

data class ApiResponse(
    val success: Boolean? ,
)