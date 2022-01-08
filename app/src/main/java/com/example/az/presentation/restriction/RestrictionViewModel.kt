package com.example.az.presentation.restriction

import android.util.Log
import android.util.Log.d
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
import retrofit2.Response
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
//            values.data?.restrictions?.let { _restrictionList.emit(convertData(it)) }

            values.data?.restrictions?.apply {
                convertData(this)
            }
            values.data?.restrictions?.let { _restrictionList.emit(getData(it)) }
            _restriction.emit(values)
        }
    }
}


interface ConvertToRestrictionKotlin {

    fun convertData(it: RestrictionResponse.Restrictions) {
        it.tbs?.apply {
            convert("TBS" , it.tbs!!)
        }
        it.gva?.apply {
            convert("GVA" , it.gva!!)
        }
        it.ber?.apply {
            convert("BER" , it.ber!!)
        }
        it.tll?.apply {
            convert("TLL" , it.tll!!)
        }
        it.rix?.apply {
            convert("RIX" , it.rix!!)
        }
    }

    fun getData(it: RestrictionResponse.Restrictions): List<RestrictionKotlin> {
        var listRestrictions: List<RestrictionKotlin> = listOf()
        it.tbs?.let {
            listRestrictions += it
        }
        it.gva?.let {
            listRestrictions += it
        }
        it.ber?.let {
            listRestrictions += it
        }
        it.tll?.let {
            listRestrictions += it
        }
        it.rix?.let {
            listRestrictions += it
        }
        return listRestrictions
    }

    fun convert(code: String , data: RestrictionKotlin): RestrictionKotlin {
        data.code = code
        return data
    }
}