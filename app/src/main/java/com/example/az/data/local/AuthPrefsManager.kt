package com.example.az.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.az.model.user.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class AuthPrefsManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AUTH_PREFS_MANAGER")

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
        Log.d(
            "testing AZ" ,
            "saved autoAuthPrefsManager saveUserDataStore $preferencesFlow"
        )
    }

    companion object {
        val AUTH_USER_TOKEN = stringPreferencesKey("AUTH_USER_TOKEN")
        val AUTH_USER_EMAIL = stringPreferencesKey("AUTH_USER_EMAIL")
        val AUTH_USER_VACCINE = stringPreferencesKey("AUTH_USER_VACCINE")
        val AUTH_USER_NATIONALITY = stringPreferencesKey("AUTH_USER_NATIONALITY")
    }

}