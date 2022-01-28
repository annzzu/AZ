package com.example.az.utils.network

import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

interface NetworkStatusTracker {

    val connectivityManager: ConnectivityManager
    val networkStatus: Flow<NetworkStatus>

}