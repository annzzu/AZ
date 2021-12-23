package com.example.az.presentation

data class UiState<T>(
    val isLoading: Boolean = true ,
    val error: Error? = null ,
    val data: List<T> = listOf() ,
) {
    enum class Error(val message: String) {
        API_ERROR("Network Error") ,
    }
}