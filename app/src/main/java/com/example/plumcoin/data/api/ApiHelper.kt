package com.example.plumcoin.data.api

import com.example.plumcoin.data.model.ApiUser

interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>
}