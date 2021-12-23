package com.example.az.presentation.airport


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.AirportRepository
import com.example.az.model.airport.Airport
import com.example.az.utils.Resource
import com.example.az.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportsViewModel @Inject constructor(private val repository: AirportRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Airport>> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState<Airport>> = _uiState

    fun getAirportsResponse() = viewModelScope.launch {
        repository.getAirports().collectLatest { value->
            when (value) {
                is Resource.Success<*> -> {
                    Log.d("coronavirus" , "${value.data}")
                    _uiState.value =  value.data?.airports?.let { UiState(data = value.data.airports, isLoading = false) }!!
                }
                is Resource.Error<*> -> {
                    _uiState.value = UiState(error = UiState.Error.API_ERROR, isLoading = false)
                }
            }
        }
    }
}