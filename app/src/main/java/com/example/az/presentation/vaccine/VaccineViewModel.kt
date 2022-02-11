package com.example.az.presentation.vaccine


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.vaccine.VaccineRepositoryImpl
import com.example.az.domain.model.vaccine.VaccineResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccineViewModel @Inject constructor(private val repository: VaccineRepositoryImpl) :
    ViewModel() {

    private val _vaccines = MutableStateFlow<Resource<VaccineResponse>>(Resource.Loading())
    val vaccines: StateFlow<Resource<VaccineResponse>> = _vaccines

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