package com.example.az.data.repository.restriction

import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.restriction.RestrictionRequest
import com.example.az.model.restriction.RestrictionResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface RestrictionRepository {

    suspend fun getRestriction(restrictionRequest: RestrictionRequest): Flow<Resource<RestrictionResponse>>

}