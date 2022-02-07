package com.example.az.presentation.nationality


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.nationality.NationalityRepositoryImpl
import com.example.az.domain.model.nationality.NationalityResponse
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NationalityViewModel @Inject constructor(private val repository: NationalityRepositoryImpl) :
    ViewModel() {

    private val _nationalities = MutableSharedFlow<Resource<NationalityResponse>>()
    val nationalities: SharedFlow<Resource<NationalityResponse>> = _nationalities

    private suspend fun getNationalities() = viewModelScope.launch {
        repository.getNationalities().collectLatest { values ->
            _nationalities.emit(values)
        }
    }

    init {
        viewModelScope.launch {
            getNationalities()
        }
    }
}