package com.example.suitmediatest.data.repository

import com.example.suitmediatest.data.model.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object UserRepository {
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    suspend fun getUsers(page: Int, perPage: Int): UserResponse {
        return apiService.getUsers(page, perPage)
    }
}

interface ApiService {
    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}
