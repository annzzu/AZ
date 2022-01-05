package com.example.az.data.repository

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.DataSource
import com.example.az.model.user.User
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                saveTokenToDataStore(result.data!!.token!!)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun logout() {
        saveTokenToDataStore("")
    }

    override suspend fun signup(user: User): Flow<Resource<User>> {
        return flow {
            emit(handleResponse { dataSource.signupUser(user) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSelf(token: String): Flow<Resource<User>> {
        return flow {
            val result = handleResponse { dataSource.getSelf(token) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun saveTokenToDataStore(token: String) {
        autoAuthPrefsManager.saveAuthToken(User(token = token))
        d("testing AZ" , "saved autoAuthPrefsManager ${autoAuthPrefsManager.readAuthToken()}")
    }

//    fun saveUserDataStore(token: String){
//        viewModelScope.launch {
//            sortList()
//            // Modify UI
//        }
//    }
}