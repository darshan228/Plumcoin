package com.example.plumcoin.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.plumcoin.data.repository.MainRepository
import com.example.plumcoin.ui.base.BaseViewModel
import com.example.plumcoin.utils.NetworkHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel<LoginNavigator>() {

    /**
     * Created by Darshan on 9/20/2022.
     * Desc: Handle returned google login result
     */
    internal fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        viewModelScope.launch {
            try {
                val account = completedTask.getResult(ApiException::class.java)
                account?.let {
                    val personName: String = it.displayName.toString()
                    Log.e(LoginActivity.TAG, "Name: $personName")
//                val personGivenName: String = it.givenName.toString()
//                val personFamilyName: String = acct.getFamilyName()
                    val personEmail: String = it.email.toString()
                    Log.e(LoginActivity.TAG, "Email: $personEmail")
//                val personId: String = acct.getId()
//                val personPhoto: Uri = acct.getPhotoUrl()
                    navigator?.showToastMessage("Welcome, $personName")
                    navigator?.openMainActivity()
                }
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e(LoginActivity.TAG, "signInResult:failed code=" + e.statusCode)
            }
        }
    }
}