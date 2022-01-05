package com.example.az.data.repository.auth

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.DataSource
import com.example.az.model.user.User
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                        getSelf(token).collectLatest {
                            if (it is Resource.Success) {
                                d(
                                    "testing AZ" ,
                                    "gamovida ${it.data!!}"
                                )
                                saveUserDataStore(it.data!!)
                            } else {
                                saveTokenToDataStore(token)
                            }
                        }
                    } catch (e: Exception) {
                        saveTokenToDataStore(token)
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

    override suspend fun getSelf(token: String): Flow<Resource<User>> {
        return flow {
            emit(handleResponse { dataSource.getSelf(token) })
        }.flowOn(IO)
    }

    private suspend fun saveTokenToDataStore(token: String) {
        autoAuthPrefsManager.saveAuthToken(User(token = token))
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