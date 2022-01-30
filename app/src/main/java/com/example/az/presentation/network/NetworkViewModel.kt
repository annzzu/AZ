package com.example.az.presentation.network

import androidx.lifecycle.ViewModel
import com.example.az.utils.network.NetworkStatusTracker
import com.example.az.utils.network.NetworkStatusTrackerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(private val networkStatus: NetworkStatusTrackerImpl) :
    ViewModel() {

    @ExperimentalCoroutinesApi
    val networkStatusActive =
        networkStatus.networkStatus

}