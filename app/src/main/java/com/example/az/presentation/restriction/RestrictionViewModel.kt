package com.example.az.presentation.restriction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.restriction.RestrictionRepositoryImpl
import com.example.az.model.restriction.*
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestrictionViewModel @Inject constructor(private val repository: RestrictionRepositoryImpl) :
    ViewModel()  {

    private val _restriction = MutableSharedFlow<Resource<RestrictionResponse>>()
    val restriction: SharedFlow<Resource<RestrictionResponse>> = _restriction

    suspend fun getRestriction(restrictionRequest: RestrictionRequest) = viewModelScope.launch {
        repository.getRestriction(restrictionRequest).collectLatest { values ->
            _restriction.emit(values)
        }
    }
}