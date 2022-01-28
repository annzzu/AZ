package com.example.az.data.remote.datasources

import com.example.az.data.remote.services.NationalityApiService
import javax.inject.Inject

class NationalityDataSource @Inject constructor(private val api: NationalityApiService) {

    suspend fun getNationalities() = api.getNationalities()

}