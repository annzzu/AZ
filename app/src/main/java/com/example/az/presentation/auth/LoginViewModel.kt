package com.example.az.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.auth.AuthRepositoryImpl
import com.example.az.model.form_states.UserFormState
import com.example.az.extensions.STRINGS
import com.example.az.extensions.emailValid
import com.example.az.extensions.passwordValid
import com.example.az.model.user.User
import com.example.az.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepositoryImpl) : ViewModel() {

    private val _login = MutableSharedFlow<Resource<User>>()
    val login: SharedFlow<Resource<User>> = _login

    suspend fun login(user: User) = viewModelScope.launch {
        repository.login(user).collectLatest { values ->
            _login.emit(values)
        }
    }

    private val _signup = MutableSharedFlow<Resource<User>>()
    val signup: SharedFlow<Resource<User>> = _signup

    fun signup(user: User) = viewModelScope.launch {
        repository.signup(user).collectLatest { values ->
            _signup.emit(values)
        }
    }

    private val _userForm = MutableSharedFlow<UserFormState>()
    val userForm: SharedFlow<UserFormState> = _userForm

    fun userValidation(email: String , password: String) {
        viewModelScope.launch {
            when {
                !email.emailValid() && !password.passwordValid() -> {
                    _userForm.emit(
                        UserFormState(
                            emailError = STRINGS.email_error ,
                            passwordError = STRINGS.password_error
                        )
                    )
                }
                !email.emailValid() -> {
                    _userForm.emit(UserFormState(emailError = STRINGS.email_error))
                }
                !password.passwordValid() -> {
                    _userForm.emit(UserFormState(passwordError = STRINGS.password_error))
                }

                else -> {
                    _userForm.emit(UserFormState(isDataValid = true))
                }
            }
        }
    }
}