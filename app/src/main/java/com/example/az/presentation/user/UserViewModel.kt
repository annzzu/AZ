package com.example.az.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.remote.ApiResponse
import com.example.az.data.repository.user.UserRepositoryImpl
import com.example.az.extensions.emailValid
import com.example.az.extensions.passwordValid
import com.example.az.model.travel_plan.TravelPlan
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.model.user.UserFormState
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepositoryImpl) : ViewModel() {

    private val _travelPlans = MutableSharedFlow<Resource<TravelPlanResponse>>()
    val travelPlans: SharedFlow<Resource<TravelPlanResponse>> = _travelPlans

    suspend fun getTravelPlan() = viewModelScope.launch {
        repository.getTravelPlan().collectLatest { values ->
            _travelPlans.emit(values)
        }
    }

    private val _createPlan = MutableSharedFlow<Resource<TravelPlanSingleResponse>>()
    val createPlan: SharedFlow<Resource<TravelPlanSingleResponse>> = _createPlan

    suspend fun createTravelPlan(travelPlan: TravelPlan) = viewModelScope.launch {
        repository.createTravelPlan(travelPlan).collectLatest { values ->
            _createPlan.emit(values)
        }
    }

    private val _updatePlan = MutableSharedFlow<Resource<TravelPlanSingleResponse>>()
    val updatePlan: SharedFlow<Resource<TravelPlanSingleResponse>> = _updatePlan

    suspend fun updateTravelPlan(travelPlan: TravelPlan) = viewModelScope.launch {
        repository.updateTravelPlan(travelPlan).collectLatest { values ->
            _updatePlan.emit(values)
        }
    }

    private val _deletePlan = MutableSharedFlow<Resource<ApiResponse>>()
    val delete: SharedFlow<Resource<ApiResponse>> = _deletePlan

    suspend fun deleteTravelPlan(id: String) = viewModelScope.launch {
        repository.deleteTravelPlan(id).collectLatest { values ->
            _deletePlan.emit(values)
        }
    }

    private val _travelPlanForm = MutableStateFlow<Boolean>(false)
    val travelPlanForm: StateFlow<Boolean> = _travelPlanForm

    fun travelPlanFormValidation(travelPlan: TravelPlan) {
        viewModelScope.launch {
            if (!travelPlan.source.isNullOrBlank()
                && travelPlan.destination.isNullOrBlank()
                && travelPlan.destination.isNullOrBlank()
            )
                _travelPlanForm.emit(true)
        }
    }

//    var source = ""
//    var destination = ""
//    var date = ""


    var source = "TBS"
    var destination = "BER"
    var date = "2022-07-25T00:00:00"
}