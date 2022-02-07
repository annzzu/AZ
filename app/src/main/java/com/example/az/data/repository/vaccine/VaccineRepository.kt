package com.example.az.data.repository.vaccine

import com.example.az.domain.model.vaccine.VaccineResponse
import com.example.az.utils.Resource
import kotlinx.coroutines.flow.Flow


interface VaccineRepository {

    suspend fun getVaccines(): Flow<Resource<VaccineResponse>>

}