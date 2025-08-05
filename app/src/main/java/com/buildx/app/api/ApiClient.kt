package com.buildx.app.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * API Client for Open All Hands local agent
 */
object ApiClient {
    
    // Base URL for the local Open All Hands agent
    // This would typically be the local server address
    private const val BASE_URL = "http://10.0.2.2:8000/" // Default for Android emulator localhost
    
    // Create OkHttpClient with logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Create Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // Create API service
    val openHandsApiService: OpenHandsApiService = retrofit.create(OpenHandsApiService::class.java)
}