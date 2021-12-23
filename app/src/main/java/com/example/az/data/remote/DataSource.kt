package com.example.az.data.remote

import javax.inject.Inject


class DataSource @Inject constructor(private val api: ApiService) {

    suspend fun getAirportData() = api.getAirportData()

}