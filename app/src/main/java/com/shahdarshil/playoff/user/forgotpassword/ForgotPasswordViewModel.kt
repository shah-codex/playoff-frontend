package com.shahdarshil.playoff.user.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahdarshil.playoff.createHash
import com.shahdarshil.playoff.network.PlayoffApi
import com.shahdarshil.playoff.network.datamodel.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by shah on 28 April, 2021.
 */
class ForgotPasswordViewModel : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + coroutineJob)

    private val _navigateToLogin = MutableLiveData<Boolean?>()

    val navigateToLogin: LiveData<Boolean?>
        get() = _navigateToLogin

    private val _changeButtonClicked = MutableLiveData<Boolean?>()

    val changeButtonClicked: LiveData<Boolean?>
        get() = _changeButtonClicked

    private val _cancelButtonClicked = MutableLiveData<Boolean?>()

    val cancelButtonClicked: LiveData<Boolean?>
        get() = _cancelButtonClicked

    fun updatePassword(email: String, password: String, otp: String) {
        coroutineScope.launch {
            val user = User(
                email = email,
                password = password.createHash(),
                otp = otp,
                username = null
            )

            val response = try {
                PlayoffApi.retrofitService.forgotPassword(user)
            } catch(e: ConnectException) {
                null
            }

            viewModelScope.launch {
                _navigateToLogin.value = response != null && response.isSuccessful
            }
        }
    }

    fun sendOtp(email: String) {
        val user = User(
            email = email,
            password = null,
            otp = null,
            username = null
        )

        coroutineScope.launch {
            try {
                PlayoffApi.retrofitService.requestOtp(user)
            } catch(e: ConnectException) {

            }
        }
    }

    fun onCancelClicked() {
        _cancelButtonClicked.value = true
    }

    fun onChangeClicked() {
        _changeButtonClicked.value = true
    }

    fun onDoneClicking() {
        _changeButtonClicked.value = null
        _cancelButtonClicked.value = null
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}