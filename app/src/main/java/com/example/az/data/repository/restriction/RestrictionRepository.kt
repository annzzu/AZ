package com.example.az.data.repository.restriction

import com.example.az.domain.model.restriction.RestrictionRequest
import com.example.az.domain.model.restriction.RestrictionResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface RestrictionRepository {

    suspend fun getRestriction(restrictionRequest: RestrictionRequest): Flow<Resource<RestrictionResponse>>

}