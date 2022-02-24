package com.example.az.presentation.airport


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.airport.AirportRepositoryImpl
import com.example.az.model.airport.AirportResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AirportsViewModel @Inject constructor(private val repository: AirportRepositoryImpl) :
    ViewModel() {

    private val _airportList = MutableSharedFlow<Resource<AirportResponse>>()
    val airportList: SharedFlow<Resource<AirportResponse>> = _airportList

    private suspend fun getAirports() = viewModelScope.launch {
        repository.getAirports().collectLatest { values ->
            _airportList.emit(values)
        }
    }

    init {
        viewModelScope.launch {
            getAirports()
        }
    }

}