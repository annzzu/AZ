package com.example.az.model.form_states

data class UserFormState(
    val emailError: Int? = null ,
    val passwordError: Int? = null ,
    val isDataValid: Boolean = false
)