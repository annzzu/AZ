package com.example.az.presentation.vaccine


import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.airport.AirportRepositoryImpl
import com.example.az.data.repository.nationality.NationalityRepositoryImpl
import com.example.az.data.repository.vaccine.VaccineRepositoryImpl
import com.example.az.model.airport.Airport
import com.example.az.model.airport.AirportResponse
import com.example.az.model.nationality.NationalityResponse
import com.example.az.model.travel_plan.TravelPlanResponse
import com.example.az.model.vaccine.VaccineResponse
import com.example.az.utils.Resource
import com.example.az.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccineViewModel @Inject constructor(private val repository: VaccineRepositoryImpl) :
    ViewModel() {

    private val _vaccines = MutableSharedFlow<Resource<VaccineResponse>>()
    val vaccines: SharedFlow<Resource<VaccineResponse>> = _vaccines

    private suspend fun getVaccines() = viewModelScope.launch {
        repository.getVaccines().collectLatest { values ->
            _vaccines.emit(values)
        }
    }

    init {
        viewModelScope.launch {
            getVaccines()
        }
    }
}