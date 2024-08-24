package com.example.plumcoin.data.api

import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers() = apiService.getUsers()


}