package com.example.az.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.remote.services.ApiResponse
import com.example.az.data.repository.user.UserRepositoryImpl
import com.example.az.extensions.dateToString
import com.example.az.model.travel_plan.TravelPlanRequest
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.travel_plan.TravelPlanSingleResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepositoryImpl) : ViewModel() {

    private val _travelPlans = MutableStateFlow<Resource<TravelPlanResponse>>(Resource.Loading())
    val travelPlans: StateFlow<Resource<TravelPlanResponse>> = _travelPlans

    suspend fun getTravelPlan() = viewModelScope.launch {
        repository.getTravelPlan().collectLatest { values ->
            _travelPlans.emit(values)
        }
    }

    private val _createPlan = MutableSharedFlow<Resource<TravelPlanSingleResponse>>()
    val createPlan: SharedFlow<Resource<TravelPlanSingleResponse>> = _createPlan

    suspend fun createTravelPlan(travelPlan: TravelPlanRequest) = viewModelScope.launch {
        travelPlan.date = Calendar.getInstance().time.dateToString()
        repository.createTravelPlan(travelPlan).collectLatest { values ->
            _createPlan.emit(values)
        }
    }

    private val _updatePlan = MutableSharedFlow<Resource<TravelPlanSingleResponse>>()
    val updatePlan: SharedFlow<Resource<TravelPlanSingleResponse>> = _updatePlan

    suspend fun updateTravelPlan(travelPlan: TravelPlanRequest) = viewModelScope.launch {
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

}