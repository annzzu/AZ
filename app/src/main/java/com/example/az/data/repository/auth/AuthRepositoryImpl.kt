package com.example.az.data.repository.auth

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.DataSource
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val dataSource: DataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) : AuthRepository {

    override suspend fun login(user: User): Flow<Resource<User>> {
        return flow {
            val result = handleResponse { dataSource.loginUser(user) }
            if (result is Resource.Success) {
                val token = result.data!!.token!!
                withContext(IO) {
                    try {
                        getSelf().collectLatest { getSelf ->
                            if (getSelf is Resource.Success) {
                                saveUserDataStore(getSelf.data!!.user!!)
                                saveAuthOnlyToken(token)
                            } else {
                                saveAuthOnlyToken(token)
                            }
                        }
                    } catch (e: Exception) {
                        saveAuthOnlyToken(token)
                    }
                }
            }
            emit(result)
        }.flowOn(IO)
    }

    override suspend fun signup(user: User): Flow<Resource<User>> {
        return flow {
            emit(handleResponse { dataSource.signupUser(user) })
        }.flowOn(IO)
    }

    override suspend fun getSelf(): Flow<Resource<UserResponse>> {
        return flow {
            val result = handleResponse { dataSource.getSelf(autoAuthPrefsManager.readAuthToken()) }
            emit(result)
        }.flowOn(IO)
    }


    private suspend fun saveAuthOnlyToken(token: String) {
        autoAuthPrefsManager.saveAuthOnlyToken(token)
        d("testing AZ" , "saved autoAuthPrefsManager ${autoAuthPrefsManager.readAuthToken()}")
    }

    private suspend fun saveUserDataStore(user: User) {
        autoAuthPrefsManager.saveAuthToken(user)
        d(
            "testing AZ" ,
            "saved autoAuthPrefsManager saveUserDataStore ${autoAuthPrefsManager.preferencesFlow}"
        )
    }
}