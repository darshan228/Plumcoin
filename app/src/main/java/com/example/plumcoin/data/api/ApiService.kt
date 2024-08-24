package com.example.plumcoin.data.api

import com.example.plumcoin.data.model.ApiUser
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<ApiUser>
}