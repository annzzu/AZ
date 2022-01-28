package com.example.az.di

import com.example.az.BuildConfig
import com.example.az.data.remote.services.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshiO(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient ,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    @Singleton
    @Provides
    fun restrictionService(retrofit: Retrofit): RestrictionApiService {
        return retrofit.create(RestrictionApiService::class.java)
    }

    @Singleton
    @Provides
    fun authService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun airportService(retrofit: Retrofit): AirportApiService {
        return retrofit.create(AirportApiService::class.java)
    }

    @Singleton
    @Provides
    fun nationalityService(retrofit: Retrofit): NationalityApiService {
        return retrofit.create(NationalityApiService::class.java)
    }

    @Singleton
    @Provides
    fun vaccineService(retrofit: Retrofit): VaccineApiService {
        return retrofit.create(VaccineApiService::class.java)
    }

    @Singleton
    @Provides
    fun travelPlanService(retrofit: Retrofit): TravelPlanApiService {
        return retrofit.create(TravelPlanApiService::class.java)
    }
}