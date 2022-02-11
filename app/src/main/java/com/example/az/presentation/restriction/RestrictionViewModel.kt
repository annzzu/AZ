package com.example.az.presentation.restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.restriction.RestrictionRepositoryImpl
import com.example.az.domain.model.restriction.*
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestrictionViewModel @Inject constructor(private val repository: RestrictionRepositoryImpl) :
    ViewModel() {

    private val _restriction = MutableStateFlow<Resource<RestrictionResponse>>(Resource.Loading())
    val restriction: StateFlow<Resource<RestrictionResponse>> = _restriction

    suspend fun getRestriction(restrictionRequest: RestrictionRequest) = viewModelScope.launch {
        repository.getRestriction(restrictionRequest).collectLatest { values ->
            _restriction.emit(values)
        }
    }

    var restrictionRequestForm = RestrictionRequest()

}