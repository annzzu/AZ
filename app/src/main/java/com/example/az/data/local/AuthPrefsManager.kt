package com.example.az.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.az.extensions.STRINGS
import com.example.az.model.user.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


class AuthPrefsManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(context.resources.getString(STRINGS.auth_prefs_manager))

    val preferencesFlow: Flow<User> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            User(
                token = it[AUTH_USER_TOKEN] ?: "" ,
                email = it[AUTH_USER_EMAIL] ?: "" ,
                data = User.Data(
                    vaccine = it[AUTH_USER_VACCINE] ?: "" ,
                    nationality = it[AUTH_USER_NATIONALITY] ?: ""
                )
            )
        }

    suspend fun readAuthToken(): String {
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_USER_TOKEN] ?: ""
    }

    suspend fun readNationality(): String {
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_USER_NATIONALITY] ?: ""
    }

    suspend fun readVaccine(): String {
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_USER_VACCINE] ?: ""
    }

    suspend fun saveAuthToken(user: User) {
        context.dataStore.edit { _dataStore ->
            _dataStore[AUTH_USER_TOKEN] = user.token ?: ""
            _dataStore[AUTH_USER_EMAIL] = user.email ?: ""
            _dataStore[AUTH_USER_VACCINE] = user.data?.vaccine ?: ""
            _dataStore[AUTH_USER_NATIONALITY] = user.data?.nationality ?: ""
        }
    }

    suspend fun saveAuthOnlyToken(token: String?) {
        context.dataStore.edit { _dataStore ->
            _dataStore[AUTH_USER_TOKEN] = token ?: ""
        }
    }

    suspend fun logout() {
        deleteUserDataStore()
    }

    private suspend fun deleteUserDataStore() {
        saveAuthToken(User())
    }

    suspend fun setAnotherTimeLaunch() {
        context.dataStore.edit { _dataStore ->
            _dataStore[IS_FIRST_TIME_LAUNCH] = false
        }
    }

    suspend fun isFirstTimeLaunch(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[IS_FIRST_TIME_LAUNCH] ?: true
    }

    companion object {
        val IS_FIRST_TIME_LAUNCH = booleanPreferencesKey("IS_FIRST_TIME_LAUNCH")

        val AUTH_USER_TOKEN = stringPreferencesKey("AUTH_USER_TOKEN")
        val AUTH_USER_EMAIL = stringPreferencesKey("AUTH_USER_EMAIL")
        val AUTH_USER_VACCINE = stringPreferencesKey("AUTH_USER_VACCINE")
        val AUTH_USER_NATIONALITY = stringPreferencesKey("AUTH_USER_NATIONALITY")
    }

}