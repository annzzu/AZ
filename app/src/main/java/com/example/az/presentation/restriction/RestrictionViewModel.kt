package com.example.az.presentation.restriction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.restriction.RestrictionRepositoryImpl
import com.example.az.data.repository.user.UserRepositoryImpl
import com.example.az.model.restriction.*
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestrictionViewModel @Inject constructor(private val repository: RestrictionRepositoryImpl) :
    ViewModel() , ConvertToRestrictionKotlin {

    private val _restriction = MutableSharedFlow<Resource<RestrictionResponse>>()
    val restriction: SharedFlow<Resource<RestrictionResponse>> = _restriction

    private val _restrictionList = MutableSharedFlow<List<RestrictionKotlin>>()
    val restrictionList: SharedFlow<List<RestrictionKotlin>> = _restrictionList

    suspend fun getRestriction(restrictionRequest: RestrictionRequest) = viewModelScope.launch {
        repository.getRestriction(restrictionRequest).collectLatest { values ->
            values.data?.restrictions?.let { _restrictionList.emit(convertData(it)) }
            _restriction.emit(values)
        }
    }
}


data class RestrictionKotlin(
    val code: String? ,
    val type: String? ,
    val generalRestrictions: GeneralRestrictions? ,
    val restrictionsByVaccination: RestrictionsByVaccination? ,
    val restrictionsByNationality: List<RestrictionsByNationality>? ,
)

interface ConvertToRestrictionKotlin {

    fun convertData(it: RestrictionResponse.Restrictions): List<RestrictionKotlin> {
        var listRestrictions: List<RestrictionKotlin> = listOf()
        it.tbs?.let {
            listRestrictions += RestrictionKotlin(
                code = "TBS" ,
                type = it.type ,
                generalRestrictions = it.generalRestrictions ,
                restrictionsByVaccination = it.restrictionsByVaccination ,
                restrictionsByNationality = it.restrictionsByNationality
            )
        }
        it.gva?.let {
            listRestrictions += RestrictionKotlin(
                code = "GVA" ,
                type = it.type ,
                generalRestrictions = it.generalRestrictions ,
                restrictionsByVaccination = it.restrictionsByVaccination ,
                restrictionsByNationality = it.restrictionsByNationality
            )
        }
        it.ber?.let {
            listRestrictions += RestrictionKotlin(
                code = "BER" ,
                type = it.type ,
                generalRestrictions = it.generalRestrictions ,
                restrictionsByVaccination = it.restrictionsByVaccination ,
                restrictionsByNationality = it.restrictionsByNationality
            )
        }
        it.tll?.let {
            listRestrictions += RestrictionKotlin(
                code = "GVA" ,
                type = it.type ,
                generalRestrictions = it.generalRestrictions ,
                restrictionsByVaccination = it.restrictionsByVaccination ,
                restrictionsByNationality = it.restrictionsByNationality
            )
        }
        it.rix?.let {
            listRestrictions += RestrictionKotlin(
                code = "RIX" ,
                type = it.type ,
                generalRestrictions = it.generalRestrictions ,
                restrictionsByVaccination = it.restrictionsByVaccination ,
                restrictionsByNationality = it.restrictionsByNationality
            )
        }
        return listRestrictions
    }
}