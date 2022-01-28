package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.VaccineApiService
import javax.inject.Inject

class VaccineDataSource @Inject constructor(private val api: VaccineApiService) {

    suspend fun getVaccines() = api.getVaccines()

}