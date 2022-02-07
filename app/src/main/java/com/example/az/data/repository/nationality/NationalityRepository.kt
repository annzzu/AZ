package com.example.az.data.repository.nationality

import com.example.az.domain.model.nationality.NationalityResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface NationalityRepository {

    suspend fun getNationalities(): Flow<Resource<NationalityResponse>>

}