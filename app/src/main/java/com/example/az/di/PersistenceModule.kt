package com.example.az.di

import android.content.Context
import com.example.az.data.local.AuthPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): AuthPrefsManager {
        return AuthPrefsManager(context)
    }

}