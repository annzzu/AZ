package com.example.az.data.repository

import com.example.az.data.remote.DataSource
import com.example.az.model.airport.AirportResponse
import com.example.az.model.user.User
import com.example.az.utils.Resource
import com.example.az.utils.handleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val dataSource: DataSource) : AuthRepository {

    override suspend fun login(user: User): Flow<Resource<User>> {
        return flow<Resource<User>> {
            val result = handleResponse { dataSource.loginUser(user) }
            if (result is Resource.Success) "save into dto"
            emit(result)
        }.flowOn(Dispatchers.IO)
    }




        //        { response ->
//                if (response.isSuccessful && response.code() == 200) {
//                    response.body()?.let { authTokenDto ->
//                        val result = saveAuthToken(authTokenDto, authLoginRequest.email)
//                        if (result < 0) {
//                            Timber.e("Couldn't save an auth token into db.")
//                        }
//                        saveAuthenticatedUserToDataStorePrefs(authLoginRequest.email)
//                        authTokenDto.let {
//                            Resource.success(
//                                authTokenDtoMapper.mapToDomainModel(it)
//                            )
//                        }
//                    } ?: returnUnknownError()
//                } else {
//                    response.errorBody()?.let { responseBody ->
//                        val errorMessage =
//                            JSONObject(responseBody.charStream().readText()).getString("error")
//                        Resource.error(
//                            errorMessage,
//                            AuthToken(
//                                errorResponse = StateResponse(
//                                    message = errorMessage,
//                                    errorResponseType = ResponseType.Dialog
//                                )
//                            )
//                        )
//                    } ?: returnUnknownError()
//                }
//            }


    override suspend fun signup(user: User): Flow<Resource<User>> {
        return flow {
            emit(handleResponse { dataSource.signupUser(user) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSelf(token: String): Flow<Resource<User>> {
        return flow {
            val result = handleResponse { dataSource.getSelf(token) }
            if (result is Resource.Success) "save into dto"
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}