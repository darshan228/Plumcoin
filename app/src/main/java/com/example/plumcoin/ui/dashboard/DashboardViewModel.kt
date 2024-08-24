package com.example.plumcoin.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.plumcoin.data.model.ApiUser
import com.example.plumcoin.data.repository.MainRepository
import com.example.plumcoin.ui.base.BaseViewModel
import com.example.plumcoin.utils.NetworkHelper
import com.example.plumcoin.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel<DashboardNavigator>() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    /**
     * Created by Darshan on 9/21/2022.
     * Desc: Get user details from network api
     */
    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                users.postValue(Resource.loading(null))
                try {
                    //withTimeout(100) {
                    val usersFromApi = mainRepository.getUsers()
                    users.postValue(Resource.success(usersFromApi))
                    //}
                } catch (e: TimeoutCancellationException) {
                    users.postValue(Resource.error("TimeoutCancellationException", null))
                } catch (e: Exception) {
                    users.postValue(Resource.error("Something Went Wrong", null))
                }
            } else {
                navigator?.showToastMessage("No Internet connection")
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }
}