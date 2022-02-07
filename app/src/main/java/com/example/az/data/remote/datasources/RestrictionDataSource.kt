package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.RestrictionApiService
import com.example.az.domain.model.restriction.RestrictionRequest
import javax.inject.Inject


class RestrictionDataSource @Inject constructor(private val api: RestrictionApiService) {

    suspend fun getRestriction(restrictionRequest: RestrictionRequest) =
        api.getRestriction(
            from = restrictionRequest.from!! ,
            to = restrictionRequest.to!! ,
            nationality = restrictionRequest.nationality ?: "" ,
            vaccine = restrictionRequest.vaccine ?: "" ,
            transfer = restrictionRequest.transfer ?: ""
        )

}