package com.example.plumcoin.ui.main

import com.example.plumcoin.data.repository.MainRepository
import com.example.plumcoin.ui.base.BaseViewModel
import com.example.plumcoin.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel<MainNavigator>() {

}