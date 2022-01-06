package com.example.az.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.user.UserRepositoryImpl
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepositoryImpl) : ViewModel() {

    private val _travelPlans = MutableSharedFlow<Resource<TravelPlanResponse>>()
    val travelPlans: SharedFlow<Resource<TravelPlanResponse>> = _travelPlans

    suspend fun getTravelPlan(token: String) = viewModelScope.launch {
        repository.getTravelPlan(token).collectLatest { values ->
            _travelPlans.emit(values)
        }
    }
}