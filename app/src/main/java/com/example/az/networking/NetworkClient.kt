package com.example.az.networking

//import com.example.az.common.ApiEndpoints
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkClient {

    @Singleton
    @Provides
    fun service() : ApiService = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory
                .create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
        )
        .baseUrl(ApiEndpoints.BASE_URL)
        .build()
        .create(ApiService::class.java)


}
