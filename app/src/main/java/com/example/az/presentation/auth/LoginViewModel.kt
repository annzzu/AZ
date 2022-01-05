package com.example.az.presentation.auth

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.az.data.repository.AuthRepository
import com.example.az.data.repository.AuthRepositoryImpl
import com.example.az.extensions.STRINGS
import com.example.az.extensions.emailValid
import com.example.az.extensions.passwordValid
import com.example.az.model.user.User
import com.example.az.model.user.UserFormState
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

    fun signup(user: User) = viewModelScope.launch {
        repository.signup(user)
    }

    private val _getSelf = MutableSharedFlow<Resource<User>>()
    val getSelf: SharedFlow<Resource<User>> = _getSelf

    fun getSelf(token: String) = viewModelScope.launch {
        repository.getSelf(token).collectLatest { values ->
            _getSelf.emit(values)
        }
    }

    private val _userForm = MutableSharedFlow<UserFormState>()
    val userForm: SharedFlow<UserFormState> = _userForm

    fun userValidation(email: String , password: String) {
        viewModelScope.launch {
            when {
                !email.emailValid() && !password.passwordValid() -> {
                    d("testing AZ viewmodel" , "email, password")
                    _userForm.emit(
                        UserFormState(
                            emailError = STRINGS.email_error ,
                            passwordError = STRINGS.password_error
                        )
                    )
                }
                !email.emailValid() -> {
                    d("testing AZ viewmodel" , "email, password - $password")
                    _userForm.emit(UserFormState(emailError = STRINGS.email_error))
                }
                !password.passwordValid() -> {
                    d("testing AZ viewmodel" , "password")
                    _userForm.emit(UserFormState(passwordError = STRINGS.password_error))
                }

                else -> {
                    _userForm.emit(UserFormState(isDataValid = true))
                }
            }


//            if (!email.emailValid() || email.isNotEmpty()) {
//                if (!email.emailValid()) {
//                    d("testing AZ viewmodel" , "email")
//                    _userForm.emit(UserFormState(emailError = STRINGS.email_error))
//                } else if (email.isNotEmpty()) {
//                    d("testing AZ viewmodel" , "password")
//                    _userForm.emit(UserFormState(passwordError = STRINGS.password_error))
//
//                } else {
//                    _userForm.emit(UserFormState(isDataValid = true))
//                }
//            }
        }
    }
}