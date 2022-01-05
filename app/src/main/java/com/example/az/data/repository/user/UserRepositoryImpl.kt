package com.example.az.data.repository.user

import android.util.Log.d
import com.example.az.data.local.AuthPrefsManager
import com.example.az.data.remote.DataSource
import com.example.az.model.user.User
import com.example.az.model.user.UserResponse
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dataSource: DataSource ,
    private val autoAuthPrefsManager: AuthPrefsManager
) : UserRepository {

    override suspend fun getSelf(token: String): Flow<Resource<UserResponse>> {
        return flow {
            val result = handleResponse { dataSource.getSelf(token) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun logout() {
        deleteUserDataStore()
    }

    override suspend fun getUserInfo() = autoAuthPrefsManager.preferencesFlow

    override suspend fun getUserToken() = autoAuthPrefsManager.readAuthToken()

    private suspend fun deleteUserDataStore() {
        autoAuthPrefsManager.saveAuthToken(User())
        d(
            "testing AZ" ,
            "saved autoAuthPrefsManager saveUserDataStore ${autoAuthPrefsManager.preferencesFlow}"
        )
    }
}

